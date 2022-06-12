import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/user/users_bloc.dart';
import 'package:flutter_app/models/user_response.dart';
import 'package:flutter_app/repositories/user/user_repository.dart';
import 'package:flutter_app/repositories/user/user_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';

class MyProfileScreen extends StatefulWidget {
  const MyProfileScreen({Key? key}) : super(key: key);

  @override
  _MyProfileScreenState createState() => _MyProfileScreenState();
}

class _MyProfileScreenState extends State<MyProfileScreen> {
  late UserRepository userRepository;
  late UsersBloc _userBloc;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    userRepository = UserRepositoryImpl();
    _userBloc = UsersBloc(userRepository)..add(const FetchMyProfile());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFFF5A5F),
          title: const Text('Mi Cuenta'),
        ),
        body: BlocProvider(
            create: (context) => _userBloc, child: _createBody(context)));
  }

  _createBody(BuildContext ctx) {
    return SingleChildScrollView(
      child: Container(
          height: MediaQuery.of(ctx).size.height * 1.0,
          color: Colors.white,
          child: BlocBuilder<UsersBloc, UsersState>(
              bloc: _userBloc,
              builder: (ctx, state) {
                if (state is UsersInitial) {
                  return const Center(child: CircularProgressIndicator());
                } else if (state is MyProfileFetched) {
                  return _myProfileForm(ctx, state.me);
                } else if (state is ProfileFetchError) {
                  _showSnackbar(context, state.message);
                }
                return const Text('Ha ocurrido un error');
              })),
    );
  }

  Widget _myProfileForm(BuildContext context, UserResponse user) {
    String name = user.fullName;
    String decodeName = utf8.decode(latin1.encode(name), allowMalformed: true);
    return Container(
      padding: const EdgeInsets.only(left: 16, top: 25, right: 16),
      child: GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
        child: ListView(
          children: [
            Center(
              child: Stack(
                children: [
                  Container(
                    width: 130,
                    height: 130,
                    decoration: BoxDecoration(
                        border: Border.all(
                            width: 4,
                            color: Theme.of(context).scaffoldBackgroundColor),
                        boxShadow: [
                          BoxShadow(
                              spreadRadius: 2,
                              blurRadius: 10,
                              color: Colors.black.withOpacity(0.1),
                              offset: const Offset(0, 10))
                        ],
                        shape: BoxShape.circle,
                        image: DecorationImage(
                            fit: BoxFit.cover,
                            image: NetworkImage(
                                PreferenceUtils.getString('AVATAR')!))),
                  ),
                  Positioned(
                      bottom: 0,
                      right: 0,
                      child: Container(
                        height: 40,
                        width: 40,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          border: Border.all(
                            width: 4,
                            color: Theme.of(context).scaffoldBackgroundColor,
                          ),
                          color: Colors.red,
                        ),
                        child: const Icon(
                          Icons.edit,
                          color: Colors.white,
                        ),
                      )),
                ],
              ),
            ),
            const SizedBox(
              height: 35,
            ),
            buildTextField("Nombre y Apellidos", decodeName),
            buildTextField("Correo Electrónico", user.email),
            buildTextField("Teléfono", user.phone),
            buildTextField(
                "Fecha de Nacimiento", user.birthDate.toIso8601String()),
            Padding(
              padding: const EdgeInsets.only(bottom: 15),
              child: Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    OutlinedButton(
                      style: OutlinedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 20, vertical: 15),
                        side: const BorderSide(
                            color: Color(0xFFD50032), width: 2),
                        shape: const RoundedRectangleBorder(
                            borderRadius: BorderRadius.all(Radius.circular(5))),
                      ),
                      child: const Text('Cancelar',
                          style: TextStyle(
                              fontSize: 20, color: Color(0xFFD50032))),
                      onPressed: () {},
                    ),
                    OutlinedButton(
                      style: OutlinedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 20, vertical: 15),
                        side: const BorderSide(
                            color: Color(0xFF385185), width: 2),
                        shape: const RoundedRectangleBorder(
                            borderRadius: BorderRadius.all(Radius.circular(5))),
                      ),
                      child: const Text('Guardar',
                          style: TextStyle(
                              fontSize: 20, color: Color(0xFF385185))),
                      onPressed: () {},
                    )
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildTextField(String labelText, String placeholder) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 35.0),
      child: TextField(
        decoration: InputDecoration(
            contentPadding: const EdgeInsets.only(bottom: 3),
            labelText: labelText,
            floatingLabelBehavior: FloatingLabelBehavior.always,
            hintText: placeholder,
            hintStyle: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Colors.black,
            )),
      ),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}
