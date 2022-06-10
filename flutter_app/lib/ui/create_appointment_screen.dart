import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/appointment/appointments_bloc.dart';
import 'package:flutter_app/blocs/image_pick/image_pick_bloc.dart';
import 'package:flutter_app/models/appointment_dto.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/bottom_navbar.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';

typedef OnPickImageCallback = void Function(
    double? maxWidth, double? maxHeight, int? quality);

class CreateAppointmentScreen extends StatefulWidget {
  final id;
  final name;
  final image;
  const CreateAppointmentScreen(
      {Key? key, required this.id, this.name, this.image})
      : super(key: key);

  @override
  _CreateAppointmentScreenState createState() =>
      _CreateAppointmentScreenState();
}

class _CreateAppointmentScreenState extends State<CreateAppointmentScreen> {
  final _formKey = GlobalKey<FormState>();
  late AppointmentRepository appointmentRepository;
  final TextEditingController _dateController = TextEditingController();
  final TextEditingController _hourController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();

  @override
  void initState() {
    PreferenceUtils.init();
    appointmentRepository = AppointmentRepositoryImpl();
    _dateController.text = "";
    _hourController.text = "";
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    String name = widget.name;
    String decodeName = utf8.decode(latin1.encode(name), allowMalformed: true);
    return Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFFF5A5F),
          title: Text('Reservar en $decodeName'),
        ),
        body: BlocProvider(
            create: (context) {
              return AppointmentsBloc(appointmentRepository);
            },
            child: createAppointmentForm(context, widget.image)));
  }

  Widget createAppointmentForm(BuildContext context, String image) {
    return Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          ClipRRect(
            child: Image(
              width: MediaQuery.of(context).size.width,
              image: NetworkImage(image),
            ),
          ),
          Padding(
              padding: const EdgeInsets.all(10.0),
              child: SizedBox(
                height: MediaQuery.of(context).size.height * 0.5,
                child: Container(
                  padding: const EdgeInsets.all(30),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 10, vertical: 10),
                          child: TextFormField(
                            readOnly: true,
                            controller: _dateController,
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.all(10),
                              filled: true,
                              fillColor: const Color(0xFFFAFAFA),
                              hintText: "Seleccione un día",
                              hintStyle: const TextStyle(
                                color: Colors.black,
                                fontSize: 13,
                                fontWeight: FontWeight.w400,
                              ),
                              focusColor: const Color(0xFFFFFFFF),
                              border: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Color(0xFFF7F7F7), width: 1.0),
                                borderRadius: BorderRadius.circular(6.0),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.grey, width: 2.0),
                                borderRadius: BorderRadius.circular(6.0),
                              ),
                            ),
                            onTap: () async {
                              DateTime? pickedDate = await showDatePicker(
                                  context: context,
                                  cancelText: 'Cancelar',
                                  confirmText: 'Seleccionar',
                                  helpText: 'Seleccione la fecha para su cita',
                                  errorInvalidText: 'Formato de fecha inválido',
                                  //locale: const Locale('es', 'ES'),
                                  initialDate: DateTime.now()
                                      .subtract(const Duration(days: 10 * 365)),
                                  firstDate: DateTime(1900),
                                  lastDate: DateTime.now().subtract(
                                      const Duration(days: 10 * 365)));

                              if (pickedDate != null) {
                                String formattedDate =
                                    DateFormat('dd-MM-yyyy').format(pickedDate);
                                setState(() {
                                  _dateController.text = formattedDate;
                                });
                              } else {
                                print("Debe seleccionar una fecha");
                              }
                            },
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Debe indicar una fecha para la cita';
                              }
                              return null;
                            },
                          )),
                      Padding(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 10, vertical: 10),
                          child: TextFormField(
                            readOnly: true,
                            controller: _hourController,
                            decoration: InputDecoration(
                              contentPadding: const EdgeInsets.all(10),
                              filled: true,
                              fillColor: const Color(0xFFFAFAFA),
                              hintText: "Seleccione una hora",
                              hintStyle: const TextStyle(
                                color: Colors.black,
                                fontSize: 13,
                                fontWeight: FontWeight.w400,
                              ),
                              focusColor: const Color(0xFFFFFFFF),
                              border: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Color(0xFFF7F7F7), width: 1.0),
                                borderRadius: BorderRadius.circular(6.0),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderSide: const BorderSide(
                                    color: Colors.grey, width: 2.0),
                                borderRadius: BorderRadius.circular(6.0),
                              ),
                            ),
                            onTap: () async {
                              TimeOfDay? pickedHour = await showTimePicker(
                                context: context,
                                initialTime: TimeOfDay.now(),
                                initialEntryMode: TimePickerEntryMode.dial,
                                cancelText: 'Cancelar',
                                confirmText: 'Seleccionar',
                                helpText: 'Seleccione la hora para su cita',
                                errorInvalidText: 'Formato de hora inválido',
                                hourLabelText: 'Hora',
                                minuteLabelText: 'Minutos',
                                builder: (BuildContext context, Widget? child) {
                                  return MediaQuery(
                                    data: MediaQuery.of(context)
                                        .copyWith(alwaysUse24HourFormat: true),
                                    child: child!,
                                  );
                                },
                              );
                              if (pickedHour != null) {
                                String formattedHour =
                                    formatTimeOfDay(pickedHour);
                                setState(() {
                                  _hourController.text = formattedHour;
                                });
                              } else {
                                print("Debe seleccionar una fecha");
                              }
                            },
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Debe indicar una hora para la cita';
                              }
                              return null;
                            },
                          )),
                      Padding(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 10),
                        child: TextFormField(
                          controller: _descriptionController,
                          style: const TextStyle(
                            fontSize: 18,
                            color: Colors.black,
                            fontWeight: FontWeight.w500,
                          ),
                          decoration: InputDecoration(
                            contentPadding: const EdgeInsets.all(10),
                            filled: true,
                            fillColor: const Color(0xFFFAFAFA),
                            hintText: "Descripción",
                            hintStyle: const TextStyle(
                              color: Colors.black,
                              fontSize: 13,
                              fontWeight: FontWeight.w400,
                            ),
                            focusColor: const Color(0xFFFFFFFF),
                            border: OutlineInputBorder(
                              borderSide: const BorderSide(
                                  color: Color(0xFFF7F7F7), width: 1.0),
                              borderRadius: BorderRadius.circular(6.0),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderSide: const BorderSide(
                                  color: Colors.grey, width: 2.0),
                              borderRadius: BorderRadius.circular(6.0),
                            ),
                          ),
                          onSaved: (String? value) {},
                        ),
                      ),
                      Padding(
                          padding: const EdgeInsets.symmetric(vertical: 20),
                          child: GestureDetector(
                            onTap: () {
                              if (_formKey.currentState!.validate()) {
                                final createAppointmentDto =
                                    CreateAppointmentDto(
                                        date: _dateController.text,
                                        hour: _hourController.text,
                                        description:
                                            _descriptionController.text);
                                BlocProvider.of<AppointmentsBloc>(context).add(
                                    CreateAppointmentEvent(
                                        widget.id, createAppointmentDto));
                                Navigator.pushNamed(context, '/bookings');
                              }
                            },
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(10),
                                ),
                                fixedSize: const Size(250, 60),
                                primary: Colors.greenAccent,
                                onPrimary: Colors.white,
                              ),
                              onPressed: () {},
                              child: GestureDetector(
                                child: const Text('Pedir Cita',
                                    style: TextStyle(fontSize: 23)),
                              ),
                            ),
                          )),
                    ],
                  ),
                ),
              ))
        ]);
  }

  String formatTimeOfDay(TimeOfDay tod) {
    final dt = DateTime(tod.hour, tod.minute);
    final format = DateFormat.jm();
    return format.format(dt);
  }
}
