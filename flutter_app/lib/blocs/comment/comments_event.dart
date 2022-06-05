part of 'comments_bloc.dart';

abstract class CommentsEvent extends Equatable {
  const CommentsEvent();

  @override
  List<Object> get props => [];
}

class FetchSiteComments extends CommentsEvent {
  final int id;

  const FetchSiteComments(this.id);

  @override
  List<Object> get props => [id];
}

class CreateCommentEvent extends CommentsEvent {
  final int id;
  final CreateCommentDto createCommentDto;
  final String imagePath;

  const CreateCommentEvent(this.id, this.createCommentDto, this.imagePath);
}
