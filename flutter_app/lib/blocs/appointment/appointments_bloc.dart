import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/appointment_dto.dart';
import 'package:flutter_app/models/appointment_response.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository.dart';
part 'appointments_event.dart';
part 'appointments_state.dart';

class AppointmentsBloc extends Bloc<AppointmentsEvent, AppointmentsState> {
  final AppointmentRepository appointmentRepository;

  AppointmentsBloc(this.appointmentRepository) : super(AppointmentsInitial()) {
    on<FetchAppointment>(_appointmentsFetched);
    on<CreateAppointmentEvent>(_createAppointmentEvent);
  }

  void _appointmentsFetched(
      FetchAppointment event, Emitter<AppointmentsState> emit) async {
    try {
      final appointments = await appointmentRepository.fetchMyAppointments();
      emit(AppointmentsFetched(appointments));
      return;
    } on Exception catch (e) {
      emit(AppointmentsFetchError(e.toString()));
    }
  }

  void _createAppointmentEvent(
      CreateAppointmentEvent event, Emitter<AppointmentsState> emit) async {
    try {
      final createAppointmentResponse = await appointmentRepository
          .createAppointment(event.id, event.createAppointmentDto);
      emit(CreateAppointmentSuccessState(createAppointmentResponse));
      return;
    } on Exception catch (e) {
      emit(CreateAppointmentErrorState(e.toString()));
    }
  }
}
