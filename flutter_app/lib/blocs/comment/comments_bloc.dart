import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/comment_response.dart';
import 'package:flutter_app/repositories/comment/comment_repository.dart';
part 'comments_event.dart';
part 'comments_state.dart';

class CommentsBloc extends Bloc<CommentsEvent, CommentsState> {
  final CommentRepository commentRepository;

  CommentsBloc(this.commentRepository) : super(CommentsInitial()) {
    on<FetchComment>(_commentsFetched);
  }

  void _commentsFetched(FetchComment event, Emitter<CommentsState> emit) async {
    try {
      final comments = await commentRepository.fetchComments();
      emit(CommentsFetched(comments));
      return;
    } on Exception catch (e) {
      emit(CommentsFetchError(e.toString()));
    }
  }
}
