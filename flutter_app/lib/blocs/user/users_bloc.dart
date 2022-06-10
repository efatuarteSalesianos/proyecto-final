import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/user_response.dart';
import 'package:flutter_app/repositories/user/user_repository.dart';
part 'users_event.dart';
part 'users_state.dart';

class UsersBloc extends Bloc<UsersEvent, UsersState> {
  final UserRepository userRepository;

  UsersBloc(this.userRepository) : super(UsersInitial()) {
    on<FetchMyProfile>(_myProfileFetched);
    on<UpdateMyProfile>(_editProfileFetched);
    on<FetchDeleteMe>(_deleteProfileFetched);
  }

  void _myProfileFetched(FetchMyProfile event, Emitter<UsersState> emit) async {
    try {
      final user = await userRepository.fetchUser();
      emit(MyProfileFetched(user));
      return;
    } on Exception catch (e) {
      emit(ProfileFetchError(e.toString()));
    }
  }

  void _editProfileFetched(
      UpdateMyProfile event, Emitter<UsersState> emit) async {
    try {
      final user = await userRepository.editUser(event.me);
      emit(MyProfileUpdated(user));
      return;
    } on Exception catch (e) {
      emit(ProfileFetchError(e.toString()));
    }
  }

  void _deleteProfileFetched(
      FetchDeleteMe event, Emitter<UsersState> emit) async {
    try {
      await userRepository.deleteUser(event.id);
      emit(const DeleteUserFetched());
      return;
    } on Exception catch (e) {
      emit(ProfileFetchError(e.toString()));
    }
  }
}
