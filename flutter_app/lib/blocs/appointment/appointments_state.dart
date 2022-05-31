part of 'appointments_bloc.dart';

abstract class AppointmentsState extends Equatable {
  const AppointmentsState();

  @override
  List<Object> get props => [];
}

class AppointmentsInitial extends AppointmentsState {}

class AppointmentsFetched extends AppointmentsState {
  final List<AppointmentResponse> appointments;

  const AppointmentsFetched(this.appointments);

  @override
  List<Object> get props => [appointments];
}

class AppointmentsFetchError extends AppointmentsState {
  final String message;
  const AppointmentsFetchError(this.message);

  @override
  List<Object> get props => [message];
}
