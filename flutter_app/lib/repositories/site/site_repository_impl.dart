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
  Future<SiteDetailResponse> fetchSiteDetails(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response =
        await http.get(Uri.parse('${Constant.apiBaseUrl}/site/$id'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return SiteDetailResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to load site detail');
    }
  }

  @override
  Future<List<SiteResponse>> fetchSitesWithType(String type) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/?type=$type'), headers: {
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
        .get(Uri.parse('${Constant.apiBaseUrl}/site/?name=$name'), headers: {
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
  Future<List<SiteResponse>> fetchSitesWithCity(String city) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/?city=$city'), headers: {
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
  Future<List<SiteResponse>> fetchSitesWithPostalCode(String postalCode) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http.get(
        Uri.parse('${Constant.apiBaseUrl}/site/?postalCode=$postalCode'),
        headers: {
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
  Future<List<SiteResponse>> fetchSitesWithRateGreaterThan(double rate) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/?rate=$rate'), headers: {
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
  Future<List<SiteResponse>> fetchFavouriteSites() async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/favourites'), headers: {
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
      throw Exception('Failed to load favourite sites');
    }
  }

  @override
  Future<void> addToFavourite(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http.post(
      Uri.parse('${Constant.apiBaseUrl}/site/$id/like'),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 201) {
      return;
    } else {
      throw Exception('Failed to add to favourite');
    }
  }

  @override
  Future<void> deleteFavourite(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http.delete(
      Uri.parse('${Constant.apiBaseUrl}/site/$id/like'),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );
    if (response.statusCode == 204) {
      return;
    } else {
      throw Exception('Failed to remove from favourite');
    }
  }
}
