import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/appointment/appointments_bloc.dart';
import 'package:flutter_app/models/appointment_response.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/shimmer_picture.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class FavouriteAppointmentList extends StatefulWidget {
  const FavouriteAppointmentList({Key? key}) : super(key: key);

  @override
  _FavouriteAppointmentListState createState() =>
      _FavouriteAppointmentListState();
}

class _FavouriteAppointmentListState extends State<FavouriteAppointmentList> {
  late AppointmentRepository appointmentRepository;
  late AppointmentsBloc _appointmentBloc;

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
            return Center(child: CircularProgressIndicator());
          } else if (state is AppointmentsFetched) {
            return _createAppointmentView(context, state.appointments);
          } else {
            return Center(child: CircularProgressIndicator());
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
          return _createAppointmentView(context, state.appointments);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createAppointmentView(
      BuildContext context, List<AppointmentResponse> appointments) {
    return ListView.builder(
      itemBuilder: (context, index) {
        return _appointmentItem(context, appointments[index]);
      },
      padding: const EdgeInsets.symmetric(vertical: 5),
      scrollDirection: Axis.vertical,
      itemCount: appointments.length,
    );
  }

  Widget _appointmentItem(
      BuildContext context, AppointmentResponse appointment) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      height: MediaQuery.of(context).size.height * 0.213,
      child: Stack(
        children: [
          Positioned.fill(
            child: ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Text(appointment.date.toIso8601String()),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
                height: 170,
                decoration: BoxDecoration(
                    borderRadius: const BorderRadius.all(Radius.circular(20)),
                    gradient: LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: [
                          Colors.black.withOpacity(0.8),
                          Colors.transparent
                        ]))),
          ),
          Positioned(
            top: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Row(
                children: [
                  Text(appointment.description,
                      style:
                          const TextStyle(color: Colors.white, fontSize: 20)),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
