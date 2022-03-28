import 'dart:convert';

import 'package:flutter_app/models/register_dto.dart';
import 'package:flutter_app/models/register_response.dart';
import 'package:flutter_app/repositories/constants.dart';
import 'package:flutter_app/repositories/register/register_repository.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';

class RegisterRepositoryImpl extends RegisterRepository {
  @override
  Future<RegisterResponse> doRegister(
      RegisterDto registerDto, String imagePath) async {
    Map<String, String> headers = {
      'Content-Type': 'multipart/form-data',
    };

    var uri = Uri.parse('${Constant.apiBaseUrl}/auth/register');

    var body = jsonEncode({
      'name': registerDto.name,
      'email': registerDto.email,
      'phone': registerDto.phone,
      'birthDate': registerDto.birthDate,
      'username': registerDto.username,
      'password': registerDto.password,
      'password2': registerDto.password2
    });

    var request = http.MultipartRequest('POST', uri)
      ..files.add(http.MultipartFile.fromString('newUser', body,
          contentType: MediaType('application', 'json')))
      ..files.add(await http.MultipartFile.fromPath('file', imagePath,
          contentType: MediaType('image', 'jpg')))
      ..headers.addAll(headers);

    final response = await request.send();

    if (response.statusCode == 201) {
      return RegisterResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      throw Exception('Failed to register');
    }
  }
}
