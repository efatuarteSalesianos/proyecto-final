part of 'appointments_bloc.dart';

abstract class AppointmentsEvent extends Equatable {
  const AppointmentsEvent();

  @override
  List<Object> get props => [];
}

class FetchAppointment extends AppointmentsEvent {
  const FetchAppointment();

  @override
  List<Object> get props => [];
}
