import 'package:flutter_app/models/appointment_dto.dart';
import 'package:flutter_app/models/appointment_response.dart';

abstract class AppointmentRepository {
  Future<List<AppointmentResponse>> fetchMyAppointments();
  Future<AppointmentResponse> fetchAppointmentById(int id);
  Future<AppointmentResponse> createAppointment(
      int id, CreateAppointmentDto createAppointmentDto);
}
