import 'dart:convert';
import 'package:flutter_app/repositories/constants.dart';
import 'package:http/http.dart';
import 'package:flutter_app/models/login_dto.dart';
import 'package:flutter_app/models/login_response.dart';
import 'package:flutter_app/repositories/login/login_repository.dart';
import 'package:shared_preferences/shared_preferences.dart';

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
      throw Exception(
          'No se ha podido iniciar sesión. Compruebe sus credenciales');
    }
  }
}

addStringToSF(String field, String value) async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  prefs.setString(field, value);
}
