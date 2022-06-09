import 'dart:convert';

import 'package:flutter_app/models/appointment_dto.dart';
import 'package:flutter_app/models/appointment_response.dart';
import 'package:flutter_app/repositories/appointment/appointment_repository.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../constants.dart';
import 'package:http_parser/http_parser.dart';

class AppointmentRepositoryImpl extends AppointmentRepository {
  final PreferenceUtils preferenceUtils = PreferenceUtils();

  @override
  Future<List<AppointmentResponse>> fetchMyAppointments() async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/appointment/me'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => AppointmentResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load appointments');
    }
  }

  @override
  Future<AppointmentResponse> fetchAppointmentById(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/appointment/{$id}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return AppointmentResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to load appointments');
    }
  }

  @override
  Future<AppointmentResponse> createAppointment(
      int id, CreateAppointmentDto createAppointmentDto) async {
    Map<String, String> headers = {
      'Content-Type': 'multipart/form-data',
    };

    var uri = Uri.parse('${Constant.apiBaseUrl}/site/$id/appointment/');

    var body = jsonEncode({
      'date': createAppointmentDto.date,
      'description': createAppointmentDto.description,
    });

    var request = http.MultipartRequest('POST', uri)
      ..files.add(http.MultipartFile.fromString('newAppointment', body,
          contentType: MediaType('application', 'json')))
      ..headers.addAll(headers);

    final response = await request.send();

    if (response.statusCode == 201) {
      return AppointmentResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      throw Exception('Failed to create appointment');
    }
  }
}
