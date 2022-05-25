import 'dart:convert';
import 'dart:ffi';
import 'package:flutter_app/models/create_site_dto.dart';
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
        await http.get(Uri.parse('${Constant.apiBaseUrl}/site'), headers: {
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
  Future<SiteResponse> fetchSiteById(Long id) async {
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

  @override
  Future<SiteDetailResponse> createSite(
      CreateSiteDto createSiteDto, String imagePath) async {
    Map<String, String> headers = {
      'Content-Type': 'multipart/form-data',
    };

    var uri = Uri.parse('${Constant.apiBaseUrl}/site/');

    var body = jsonEncode({
      'name': createSiteDto.name,
      'description': createSiteDto.description,
      'address': createSiteDto.address,
      'city': createSiteDto.city,
      'postalCode': createSiteDto.postalCode,
      'email': createSiteDto.email,
      'phone': createSiteDto.phone,
      'web': createSiteDto.web,
      'openingHour': createSiteDto.openingHour,
      'closingHour': createSiteDto.closingHour,
    });

    var request = http.MultipartRequest('POST', uri)
      ..files.add(http.MultipartFile.fromString('newUser', body,
          contentType: MediaType('application', 'json')))
      ..files.add(await http.MultipartFile.fromPath('file', imagePath,
          contentType: MediaType('image', 'jpg')))
      ..headers.addAll(headers);

    final response = await request.send();

    if (response.statusCode == 201) {
      return SiteDetailResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      throw Exception('Failed to create site');
    }
  }
}
