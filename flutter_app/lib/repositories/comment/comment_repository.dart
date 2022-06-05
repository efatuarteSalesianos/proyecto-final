import 'package:flutter_app/models/comment_dto.dart';
import 'package:flutter_app/models/comment_response.dart';

abstract class CommentRepository {
  Future<List<CommentResponse>> fetchComments(int id);
  Future<CommentResponse> fetchCommentById(int id);
  Future<CommentResponse> createComment(
      int id, CreateCommentDto createCommentDto, String imagePath);
}
