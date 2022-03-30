import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/login/login_bloc.dart';
import 'package:flutter_app/models/login_dto.dart';
import 'package:flutter_app/repositories/login/login_repository.dart';
import 'package:flutter_app/repositories/login/login_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/bottom_navbar.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  late LoginRepository loginRepository;
  final _formKey = GlobalKey<FormState>();
  TextEditingController usernameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  void initState() {
    PreferenceUtils.init();
    loginRepository = LoginRepositoryImpl();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) {
          return LoginBloc(loginRepository);
        },
        child: _createBody(context));
  }

  _createBody(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
            height: MediaQuery.of(context).size.height,
            color: const Color(0xFFE1E1E1),
            padding: const EdgeInsets.symmetric(vertical: 90, horizontal: 20),
            child: BlocConsumer<LoginBloc, LoginState>(
                listenWhen: (context, state) {
              return state is LoginSuccessState || state is LoginErrorState;
            }, listener: (context, state) {
              if (state is LoginSuccessState) {
                PreferenceUtils.setString('TOKEN', state.loginResponse.token);
                PreferenceUtils.setString(
                    'USERNAME', state.loginResponse.username);
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => BottomNavbarScreen(
                          token: state.loginResponse.token,
                          username: state.loginResponse.username)),
                );
              } else if (state is LoginErrorState) {
                _showSnackbar(context, state.message);
              }
            }, buildWhen: (context, state) {
              return state is LoginInitialState || state is LoginLoadingState;
            }, builder: (context, state) {
              if (state is LoginInitialState) {
                return buildForm(context);
              } else if (state is LoginLoadingState) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return buildForm(context);
              }
            })),
      ),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  Widget buildForm(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        SizedBox(
          height: 460,
          child: Card(
            color: Colors.white,
            semanticContainer: true,
            clipBehavior: Clip.antiAliasWithSaveLayer,
            child: Form(
              key: _formKey,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 30),
                    child: SvgPicture.asset('assets/images/logo.svg',
                        width: 200, semanticsLabel: 'MySalon Logo'),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 30),
                    child: TextFormField(
                        controller: usernameController,
                        style: const TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w500,
                        ),
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (String? value) {
                          return (value == '')
                              ? 'Introduzca su nombre de usuario.'
                              : null;
                        },
                        decoration: InputDecoration(
                          contentPadding: const EdgeInsets.all(10),
                          filled: true,
                          fillColor: const Color(0xFFFAFAFA),
                          hintText: "Teléfono, usuario o correo electrónico",
                          hintStyle: const TextStyle(
                            color: Colors.black,
                            fontSize: 13,
                            fontWeight: FontWeight.w400,
                          ),
                          focusColor: const Color(0xFFFFFFFF),
                          border: OutlineInputBorder(
                            borderSide: const BorderSide(
                                color: Color(0xFFF7F7F7), width: 1.0),
                            borderRadius: BorderRadius.circular(6.0),
                          ),
                          focusedBorder: OutlineInputBorder(
                            borderSide: const BorderSide(
                                color: Colors.grey, width: 2.0),
                            borderRadius: BorderRadius.circular(6.0),
                          ),
                        )),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 30, vertical: 10),
                    child: TextFormField(
                        controller: passwordController,
                        obscureText: true,
                        style: const TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                          fontWeight: FontWeight.w500,
                        ),
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (value) {
                          return (value == null || value.isEmpty)
                              ? 'Debe introducir su contraseña'
                              : null;
                        },
                        decoration: InputDecoration(
                          contentPadding: const EdgeInsets.all(10),
                          filled: true,
                          fillColor: const Color(0xFFFAFAFA),
                          hintText: "Contraseña",
                          hintStyle: const TextStyle(
                            color: Colors.black,
                            fontSize: 13,
                            fontWeight: FontWeight.w400,
                          ),
                          focusColor: const Color(0xFFFFFFFF),
                          border: OutlineInputBorder(
                            borderSide: const BorderSide(
                                color: Color(0xFFF7F7F7), width: 1.0),
                            borderRadius: BorderRadius.circular(6.0),
                          ),
                          focusedBorder: OutlineInputBorder(
                            borderSide: const BorderSide(
                                color: Colors.grey, width: 2.0),
                            borderRadius: BorderRadius.circular(6.0),
                          ),
                        )),
                  ),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      fixedSize: const Size(288, 50),
                      primary: const Color(0xFFFF5A5F),
                      onPrimary: Colors.white,
                    ),
                    onPressed: () {
                      Navigator.of(context).push(MaterialPageRoute(
                          builder: (context) => const BottomNavbarScreen()));
                    },
                    child: GestureDetector(
                      onTap: () {
                        if (_formKey.currentState!.validate()) {
                          final loginDto = LoginDto(
                              username: usernameController.text,
                              password: passwordController.text);

                          BlocProvider.of<LoginBloc>(context)
                              .add(DoLoginEvent(loginDto));
                        }
                      },
                      child: const Text(
                        'Iniciar Sesión',
                        style: TextStyle(fontSize: 16),
                      ),
                    ),
                  ),
                  Column(children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20.0),
                      child: Row(children: [
                        Expanded(
                          child: Container(
                              margin: const EdgeInsets.only(
                                  left: 10.0, right: 20.0),
                              child: const Divider(
                                color: Colors.black,
                                height: 36,
                              )),
                        ),
                        const Text(
                          "〇",
                          style: TextStyle(
                            color: Colors.black,
                          ),
                        ),
                        Expanded(
                          child: Container(
                              margin: const EdgeInsets.only(
                                  left: 20.0, right: 10.0),
                              child: const Divider(
                                color: Colors.black,
                                height: 36,
                              )),
                        ),
                      ]),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(bottom: 30),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          SvgPicture.asset('assets/images/icons/fb.svg',
                              width: 30, semanticsLabel: 'Facebook'),
                          const Padding(
                            padding: EdgeInsets.only(left: 5),
                            child: Text(
                              'Iniciar sesión con Facebook',
                              style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  fontSize: 17,
                                  color: Color(0xFF385185)),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: const [
                        Text(
                          '¿Has olvidado tu contraseña?',
                          style:
                              TextStyle(fontSize: 15, color: Color(0xFF00376B)),
                        ),
                      ],
                    ),
                  ])
                ],
              ),
            ),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10.0),
            ),
            elevation: 10,
          ),
        ),
        SizedBox(
          width: 500,
          height: 100,
          child: Card(
            color: Colors.white,
            semanticContainer: true,
            clipBehavior: Clip.antiAliasWithSaveLayer,
            child: Container(
              padding: const EdgeInsets.all(20),
              child: TextButton(
                  onPressed: () => Navigator.pushNamed(context, '/register'),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Text(
                        '¿No tienes una cuenta?',
                        style:
                            TextStyle(fontSize: 16, color: Color(0xFF262626)),
                      ),
                      TextButton(
                          onPressed: () =>
                              Navigator.pushNamed(context, '/register'),
                          child: const Text(
                            'Regístrate',
                            style: TextStyle(
                                fontSize: 16, color: Color(0xFFFF5A5F)),
                          )),
                    ],
                  )),
            ),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10.0),
            ),
            elevation: 10,
          ),
        ),
      ],
    );
  }
}
