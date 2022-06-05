import 'dart:convert';

import 'package:flutter_app/models/comment_dto.dart';
import 'package:flutter_app/models/comment_response.dart';
import 'package:flutter_app/repositories/comment/comment_repository.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../constants.dart';
import 'package:http_parser/http_parser.dart';

class CommentRepositoryImpl extends CommentRepository {
  final PreferenceUtils preferenceUtils = PreferenceUtils();

  @override
  Future<List<CommentResponse>> fetchComments(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/site/$id/comment/'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return (jsonResponse as List)
          .map((data) => CommentResponse.fromJson(data))
          .toList();
    } else {
      throw Exception('Failed to load comments from this site');
    }
  }

  @override
  Future<CommentResponse> fetchCommentById(int id) async {
    String token = PreferenceUtils.getString('TOKEN')!;

    final response = await http
        .get(Uri.parse('${Constant.apiBaseUrl}/comment/{$id}'), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $token',
    });
    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body);
      return CommentResponse.fromJson(jsonResponse);
    } else {
      throw Exception('Failed to load comments');
    }
  }

  @override
  Future<CommentResponse> createComment(
      int id, CreateCommentDto createCommentDto, String imagePath) async {
    Map<String, String> headers = {
      'Content-Type': 'multipart/form-data',
    };

    var uri = Uri.parse('${Constant.apiBaseUrl}/site/$id/comment/');

    var body = jsonEncode({
      'title': createCommentDto.title,
      'description': createCommentDto.description,
      'rate': createCommentDto.rate,
    });

    var request = http.MultipartRequest('POST', uri)
      ..files.add(http.MultipartFile.fromString('newComment', body,
          contentType: MediaType('application', 'json')))
      ..files.add(await http.MultipartFile.fromPath('file', imagePath,
          contentType: MediaType('image', 'jpg')))
      ..headers.addAll(headers);

    final response = await request.send();

    if (response.statusCode == 201) {
      return CommentResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      throw Exception('Failed to create comment');
    }
  }
}
