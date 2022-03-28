import 'dart:convert';
import 'package:flutter_app/repositories/constants.dart';
import 'package:http/http.dart';
import 'package:flutter_app/models/login_dto.dart';
import 'package:flutter_app/models/login_response.dart';
import 'package:flutter_app/repositories/login/login_repository.dart';

class LoginRepositoryImpl extends LoginRepository {
  final Client _client = Client();

  @override
  Future<LoginResponse> login(LoginDto loginDto) async {
    final response = await _client.post(
        Uri.parse('${Constant.apiBaseUrl}/auth/login'),
        body: jsonEncode(loginDto.toJson()),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        });
    if (response.statusCode == 201) {
      return LoginResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to login');
    }
  }
}
