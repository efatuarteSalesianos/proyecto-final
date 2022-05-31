part of 'comments_bloc.dart';

abstract class CommentsState extends Equatable {
  const CommentsState();

  @override
  List<Object> get props => [];
}

class CommentsInitial extends CommentsState {}

class CommentsFetched extends CommentsState {
  final List<CommentResponse> comments;

  const CommentsFetched(this.comments);

  @override
  List<Object> get props => [comments];
}

class CommentsFetchError extends CommentsState {
  final String message;
  const CommentsFetchError(this.message);

  @override
  List<Object> get props => [message];
}
