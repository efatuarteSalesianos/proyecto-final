import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/appointment/appointments_bloc.dart';
import 'package:flutter_app/blocs/appointment/appointments_bloc.dart';
import 'package:flutter_app/models/appointment_response.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/rating_bar_widget.dart';
import 'package:flutter_app/widgets/shimmer_picture.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:like_button/like_button.dart';

class BookingsSiteList extends StatefulWidget {
  const BookingsSiteList({Key? key}) : super(key: key);

  @override
  _BookingsSiteListState createState() => _BookingsSiteListState();
}

class _BookingsSiteListState extends State<BookingsSiteList> {
  late AppointmentRepository appointmentRepository;
  late AppointmentsBloc _appointmentBloc;
  var myMenuItems = <String>[
    'Editar',
    'Eliminar',
  ];

  void onSelect(item) {
    switch (item) {
      case 'Editar':
        print('Editar clicked');
        break;
      case 'Eliminar':
        print('Eliminar clicked');
        break;
    }
  }

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    appointmentRepository = AppointmentRepositoryImpl();
    _appointmentBloc = AppointmentsBloc(appointmentRepository)
      ..add(const FetchAppointment());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: ((context) => _appointmentBloc),
        child: BlocBuilder<AppointmentsBloc, AppointmentsState>(
            builder: (context, state) {
          if (state is AppointmentsInitial) {
            return const Center(child: CircularProgressIndicator());
          } else if (state is AppointmentsFetched) {
            return _createBody(context);
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        }));
  }

  Widget _createBody(BuildContext context) {
    return BlocBuilder<AppointmentsBloc, AppointmentsState>(
      bloc: _appointmentBloc,
      builder: (context, state) {
        if (state is AppointmentsInitial) {
          return Column(
            children: const [
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
            ],
          );
        } else if (state is AppointmentsFetched) {
          return _createAppointmentsView(context, state.appointments);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createAppointmentsView(
      BuildContext context, List<AppointmentResponse> appointments) {
    return Container(
      decoration: const BoxDecoration(
        color: Color(0xFFE5E5E5),
      ),
      child: ListView.builder(
        itemBuilder: (context, index) {
          return GestureDetector(
              onTap: () {},
              child: _appointmentItem(context, appointments[index]));
        },
        padding: const EdgeInsets.symmetric(vertical: 5),
        scrollDirection: Axis.vertical,
        itemCount: appointments.length,
      ),
    );
  }

  Widget _appointmentItem(
      BuildContext context, AppointmentResponse appointment) {
    String siteName = appointment.site;
    String decodedSiteName =
        utf8.decode(latin1.encode(siteName), allowMalformed: true);
    String appointmentComment = appointment.description;
    String decodedAppointmentComment =
        utf8.decode(latin1.encode(appointmentComment), allowMalformed: true);
    return Container(
        margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
        height: MediaQuery.of(context).size.height * 0.20,
        child: Card(
            elevation: 10,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(
                  width: MediaQuery.of(context).size.width * 0.23,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10.0),
                        child: Column(
                          children: [
                            Padding(
                              padding: const EdgeInsets.only(left: 4.0),
                              child: Text(getDayFromDate(appointment.date),
                                  style: const TextStyle(
                                      fontSize: 35,
                                      color: Color(0xFFFF5A5F),
                                      fontWeight: FontWeight.bold)),
                            ),
                            Text(getMonthFromDate(appointment.date),
                                style: const TextStyle(
                                    fontSize: 35,
                                    color: Color(0xFFFF5A5F),
                                    fontWeight: FontWeight.bold)),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
                const VerticalDivider(
                  color: Colors.black26,
                  thickness: 1,
                ),
                Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 13.0),
                          child: Text(decodedSiteName,
                              style: const TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w700)),
                        ),
                        PopupMenuButton<String>(
                            onSelected: onSelect,
                            itemBuilder: (BuildContext context) {
                              return myMenuItems.map((String choice) {
                                return PopupMenuItem<String>(
                                  value: choice,
                                  child: Text(choice),
                                );
                              }).toList();
                            })
                      ],
                    ),
                    Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                            width: MediaQuery.of(context).size.width * 0.5,
                            child: Text(decodedAppointmentComment,
                                softWrap: true,
                                maxLines: 3,
                                overflow: TextOverflow.ellipsis,
                                style: const TextStyle(fontSize: 16)),
                          ),
                        ]),
                    Padding(
                      padding: const EdgeInsets.only(top: 25.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(top: 4.0),
                            child: Row(
                              children: [
                                const Icon(
                                  Icons.access_time,
                                  color: Colors.black,
                                  size: 22,
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(left: 3),
                                  child: Text(appointment.hour,
                                      style: const TextStyle(
                                          fontSize: 21,
                                          color: Colors.black,
                                          fontWeight: FontWeight.bold)),
                                ),
                              ],
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(left: 55.0),
                            child: Row(
                              children: [
                                Text(
                                  "◉",
                                  style: TextStyle(
                                      color:
                                          getColorForStatus(appointment.status),
                                      fontSize: 20),
                                ),
                                Padding(
                                  padding:
                                      const EdgeInsets.fromLTRB(3.0, 2.0, 0, 0),
                                  child: Text(
                                    appointment.status,
                                    style: TextStyle(
                                        color: getColorForStatus(
                                            appointment.status),
                                        fontSize: 16,
                                        fontWeight: FontWeight.w800),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ],
            )));
  }

  static const ESPERA = "ESPERA";
  static const ACEPTADA = "ACEPTADA";
  static const CANCELADA = "CANCELADA";

  static const _colorMap = {
    CANCELADA: Color(0xFFCC0000),
    ACEPTADA: Color(0xFF6AA84F),
    ESPERA: Color(0xFFF1C232)
  };

  static getColorForStatus(String status) => _colorMap[status];

  static const _colorMapText = {
    CANCELADA: Colors.white,
    ACEPTADA: Colors.white,
    ESPERA: Colors.black
  };

  static getColorTextForStatus(String status) => _colorMapText[status];

  String getDayFromDate(String date) {
    return date.split("-")[1];
  }

  String getMonthFromDate(String date) {
    String month = date.split("-")[2];
    switch (month) {
      case "01":
        return "Ene";
      case "02":
        return "Feb";
      case "03":
        return "Mar";
      case "04":
        return "Abr";
      case "05":
        return "May";
      case "06":
        return "Jun";
      case "07":
        return "Jul";
      case "08":
        return "Ago";
      case "09":
        return "Sep";
      case "10":
        return "Oct";
      case "11":
        return "Nov";
      case "12":
        return "Dic";
      default:
        return "";
    }
  }
}
