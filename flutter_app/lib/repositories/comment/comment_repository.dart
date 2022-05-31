import 'package:flutter_app/models/comment_dto.dart';
import 'package:flutter_app/models/comment_response.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';

abstract class CommentRepository {
  Future<List<CommentResponse>> fetchComments();
  Future<List<CommentResponse>> fetchCommentsWithType(String type);
  Future<List<CommentResponse>> fetchCommentsWithName(String name);
  Future<List<CommentResponse>> fetchCommentsWithCity(String city);
  Future<List<CommentResponse>> fetchCommentsWithRateGreaterThan(double rate);
  Future<CommentResponse> fetchCommentById(int id);
  Future<CommentResponse> createComment(
      CreateCommentDto createCommentDto, String imagePath);
}
