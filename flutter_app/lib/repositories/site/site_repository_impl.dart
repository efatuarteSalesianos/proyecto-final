import 'dart:convert';

import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../constants.dart';
import 'package:http_parser/http_parser.dart';

class SiteRepositoryImpl extends SiteRepository {
  final PreferenceUtils preferenceUtils = PreferenceUtils();

  @override
  Future<List<SiteResponse>> fetchSites() async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response =
        await http.get(Uri.parse('${Constant.apiBaseUrl}/site/'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => SiteResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load sites');
    }
  }

  @override
  Future<List<SiteResponse>> fetchSitesWithType(String type) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/{$type}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => SiteResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load sites');
    }
  }

  @override
  Future<List<SiteResponse>> fetchSitesWithName(String name) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/{$name}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => SiteResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load sites');
    }
  }

  @override
  Future<List<SiteResponse>> fetchSitesWithRateGreaterThan(int rate) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/{$rate}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => SiteResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load sites');
    }
  }

  @override
  Future<SiteResponse> fetchSiteById(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/{$id}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return SiteResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to load sites');
    }
  }
}
