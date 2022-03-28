import 'package:flutter_app/models/login_dto.dart';
import 'package:flutter_app/models/login_response.dart';

abstract class LoginRepository {
  Future<LoginResponse> login(LoginDto loginDto);
}
