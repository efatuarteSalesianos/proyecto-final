part of 'comments_bloc.dart';

abstract class CommentsEvent extends Equatable {
  const CommentsEvent();

  @override
  List<Object> get props => [];
}

class FetchComment extends CommentsEvent {
  const FetchComment();

  @override
  List<Object> get props => [];
}
