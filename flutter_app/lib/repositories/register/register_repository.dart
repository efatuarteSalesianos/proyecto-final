import 'package:flutter_app/models/register_dto.dart';
import 'package:flutter_app/models/register_response.dart';

abstract class RegisterRepository {
  Future<RegisterResponse> doRegister(
      RegisterDto registerDto, String imagePath);
}
