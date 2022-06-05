import 'package:flutter/material.dart';
import 'package:flutter_app/ui/booking_done_screen.dart';
import 'package:flutter_app/ui/login_screen.dart';

class CreateAppointmentScreen extends StatefulWidget {
  final id;
  const CreateAppointmentScreen({Key? key, required this.id}) : super(key: key);

  @override
  _CreateAppointmentScreenState createState() =>
      _CreateAppointmentScreenState();
}

class _CreateAppointmentScreenState extends State<CreateAppointmentScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        shadowColor: const Color(0xFFFF5A5F),
        backgroundColor: const Color(0xFFFF5A5F),
        foregroundColor: const Color(0xFFFFFFFF),
        title: const Text('Crear Cita'),
        actions: [
          IconButton(
              icon: const Icon(Icons.exit_to_app),
              onPressed: () => {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => const LoginScreen()))
                  }),
        ],
      ),
      body: const DateTimePicker(),
    );
  }
}

class DateTimePicker extends StatefulWidget {
  const DateTimePicker({Key? key}) : super(key: key);

  @override
  _DateTimePickerState createState() => _DateTimePickerState();
}

class _DateTimePickerState extends State<DateTimePicker> {
  String date = "";
  DateTime selectedDate = DateTime.now().toLocal();
  TimeOfDay selectedTime = TimeOfDay.now();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 40, horizontal: 20),
      child: Column(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Column(
            children: [
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  primary: Colors.white,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                ),
                onPressed: () {
                  _selectDate(context);
                },
                child: Container(
                  alignment: Alignment.center,
                  height: 60.0,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Row(
                        children: <Widget>[
                          Row(
                            children: <Widget>[
                              const Icon(
                                Icons.date_range,
                                size: 25.0,
                                color: Color(0xFFFF5A5F),
                              ),
                              Padding(
                                padding: const EdgeInsets.only(left: 10.0),
                                child: Text(
                                  "${selectedDate.day}/${selectedDate.month}/${selectedDate.year}",
                                  style: const TextStyle(
                                      color: Color(0xFFFF5A5F),
                                      fontWeight: FontWeight.bold,
                                      fontSize: 22.0),
                                ),
                              ),
                            ],
                          )
                        ],
                      ),
                      const Text(
                        "  Seleccionar",
                        style: TextStyle(
                            color: Color(0xFFFF5A5F),
                            fontWeight: FontWeight.bold,
                            fontSize: 22.0),
                      ),
                    ],
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 30.0),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    primary: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                  ),
                  onPressed: () {
                    _selectTime(context);
                  },
                  child: Container(
                    alignment: Alignment.center,
                    height: 60.0,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        Row(
                          children: <Widget>[
                            Row(
                              children: <Widget>[
                                const Icon(
                                  Icons.access_time,
                                  size: 25.0,
                                  color: Color(0xFFFF5A5F),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(left: 10.0),
                                  child: Text(
                                    "${selectedTime.hour}:${selectedTime.minute}",
                                    style: const TextStyle(
                                        color: Color(0xFFFF5A5F),
                                        fontWeight: FontWeight.bold,
                                        fontSize: 22.0),
                                  ),
                                ),
                              ],
                            )
                          ],
                        ),
                        const Text(
                          "  Seleccionar",
                          style: TextStyle(
                              color: Color(0xFFFF5A5F),
                              fontWeight: FontWeight.bold,
                              fontSize: 22.0),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
          Padding(
            padding:
                const EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                primary: const Color(0xFF5AD9A4),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
              ),
              onPressed: () => {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => BookingDoneScreen(
                              name: null,
                              image: null,
                            )))
              },
              child: Container(
                alignment: Alignment.center,
                height: 60.0,
                child: const Text(
                  "Reservar",
                  style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 30.0),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  _selectDate(BuildContext context) async {
    final DateTime? selected = await showDatePicker(
        context: context,
        initialDate: selectedDate,
        firstDate: DateTime.now(),
        lastDate: DateTime.now().add(const Duration(days: 50)),
        helpText: "¿QUÉ DÍA DESEAS RESERVAR?",
        cancelText: "CANCELAR",
        confirmText: "SELECCIONAR",
        initialDatePickerMode: DatePickerMode.day);
    if (selected != null && selected != selectedDate) {
      setState(() {
        selectedDate = selected;
      });
    }
  }

  _selectTime(BuildContext context) async {
    final TimeOfDay? timeOfDay = await showTimePicker(
      context: context,
      initialTime: selectedTime,
      initialEntryMode: TimePickerEntryMode.dial,
      helpText: "¿QUÉ HORA DESEAS RESERVAR?",
      cancelText: "CANCELAR",
      confirmText: "SELECCIONAR",
    );
    if (timeOfDay != null && timeOfDay != selectedTime) {
      setState(() {
        selectedTime = timeOfDay;
      });
    }
  }
}
