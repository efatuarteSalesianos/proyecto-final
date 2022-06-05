import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/comment_dto.dart';
import 'package:flutter_app/models/comment_response.dart';
import 'package:flutter_app/repositories/comment/comment_repository.dart';
part 'comments_event.dart';
part 'comments_state.dart';

class CommentsBloc extends Bloc<CommentsEvent, CommentsState> {
  final CommentRepository commentRepository;

  CommentsBloc(this.commentRepository) : super(CommentsInitial()) {
    on<FetchSiteComments>(_commentsFetched);
  }

  void _commentsFetched(
      FetchSiteComments event, Emitter<CommentsState> emit) async {
    try {
      final comments = await commentRepository.fetchComments(event.id);
      emit(CommentsFetched(comments));
      return;
    } on Exception catch (e) {
      emit(CommentsFetchError(e.toString()));
    }
  }

  void _createCommentEvent(
      CreateCommentEvent event, Emitter<CommentsState> emit) async {
    try {
      final createCommentResponse = await commentRepository.createComment(
          event.id, event.createCommentDto, event.imagePath);
      emit(CreateCommentSuccessState(createCommentResponse));
      return;
    } on Exception catch (e) {
      emit(CreateCommentErrorState(e.toString()));
    }
  }
}
