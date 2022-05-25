import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/models/user_response.dart';

abstract class UserRepository {
  Future<UserResponse> fetchUser();
}
