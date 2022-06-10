part of 'users_bloc.dart';

abstract class UsersState extends Equatable {
  const UsersState();

  @override
  List<Object> get props => [];
}

class UsersInitial extends UsersState {}

class MyProfileFetched extends UsersState {
  final UserResponse me;
  const MyProfileFetched(this.me);

  @override
  List<Object> get props => [me];
}

class MyProfileUpdated extends UsersState {
  final UserResponse me;

  const MyProfileUpdated(this.me);

  @override
  List<Object> get props => [me];
}

class DeleteUserFetched extends UsersState {
  const DeleteUserFetched();

  @override
  List<Object> get props => [];
}

class ProfileFetchError extends UsersState {
  final String message;
  const ProfileFetchError(this.message);

  @override
  List<Object> get props => [message];
}
