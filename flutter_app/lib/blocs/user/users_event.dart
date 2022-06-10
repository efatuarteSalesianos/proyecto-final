part of 'users_bloc.dart';

abstract class UsersEvent extends Equatable {
  const UsersEvent();

  @override
  List<Object> get props => [];
}

class FetchMyProfile extends UsersEvent {
  const FetchMyProfile();

  @override
  List<Object> get props => [];
}

class UpdateMyProfile extends UsersEvent {
  final UserResponse me;

  const UpdateMyProfile(this.me);

  @override
  List<Object> get props => [me];
}

class FetchDeleteMe extends UsersEvent {
  final String id;
  const FetchDeleteMe(this.id);

  @override
  List<Object> get props => [id];
}
