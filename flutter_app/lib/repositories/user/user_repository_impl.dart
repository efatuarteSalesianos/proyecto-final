import 'dart:convert';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/models/user_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/user/user_repository.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../constants.dart';

class UserRepositoryImpl extends UserRepository {
  final PreferenceUtils preferenceUtils = PreferenceUtils();

  @override
  Future<UserResponse> fetchUser() async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/profile/me'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return UserResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to load profile');
    }
  }

  @override
  Future<UserResponse> editUser(UserResponse user) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http.put(
      Uri.parse('${Constant.apiBaseUrl}/me'),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: json.encode(user.toJson()),
    );
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return UserResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to edit profile');
    }
  }

  @override
  Future<void> deleteUser(String id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http.delete(
      Uri.parse('${Constant.apiBaseUrl}/delete/$id'),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 200) {
      return;
    } else {
      throw Exception('Failed to delete profile');
    }
  }
}
