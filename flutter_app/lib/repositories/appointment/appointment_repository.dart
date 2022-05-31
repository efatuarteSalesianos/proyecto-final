import 'package:flutter_app/models/appointment_dto.dart';
import 'package:flutter_app/models/appointment_response.dart';

abstract class AppointmentRepository {
  Future<List<AppointmentResponse>> fetchAppointments();
  Future<AppointmentResponse> fetchAppointmentById(int id);
  Future<AppointmentResponse> createAppointment(
      CreateAppointmentDto createAppointmentDto);
}