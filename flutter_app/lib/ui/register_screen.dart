import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/image_pick/image_pick_bloc.dart';
import 'package:flutter_app/blocs/register/register_bloc.dart';
import 'package:flutter_app/models/register_dto.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/bottom_navbar.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';

typedef OnPickImageCallback = void Function(
    double? maxWidth, double? maxHeight, int? quality);

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  XFile? _imageFile;

  final _formKey = GlobalKey<FormState>();

  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _dateController = TextEditingController();
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController =
      TextEditingController();

  late String avatarPath = PreferenceUtils.getString('AVATAR')!;

  set _setImageFile(XFile? value) {
    _imageFile = value ?? null;
  }

  @override
  void initState() {
    PreferenceUtils.init();
    _dateController.text = "";
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: BlocProvider(
        create: (context) {
          return ImagePickBloc();
        },
        child: BlocConsumer<ImagePickBloc, ImagePickState>(
            listenWhen: (context, state) {
              return state is ImageSelectedSuccessState;
            },
            listener: (context, state) {},
            buildWhen: (context, state) {
              return state is ImagePickInitial ||
                  state is ImageSelectedSuccessState;
            },
            builder: (context, state) {
              if (state is ImageSelectedSuccessState) {
                PreferenceUtils.setString('AVATAR', state.pickedFile.path);
              }
              return registerForm(context, state);
            }),
      ),
    );
  }

  Widget registerForm(BuildContext context, state) {
    return MaterialApp(
      home: Container(
        decoration: const BoxDecoration(color: Color(0xFFFF5A5F)),
        child: ListView(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 15),
              child: Column(
                children: [
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 1.6,
                    child: Card(
                      color: Colors.white,
                      semanticContainer: true,
                      clipBehavior: Clip.antiAliasWithSaveLayer,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0),
                      ),
                      elevation: 10,
                      child: Container(
                        padding: const EdgeInsets.all(30),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Padding(
                              padding: const EdgeInsets.only(bottom: 20),
                              child: SvgPicture.asset('assets/images/logo.svg',
                                  width: 200, semanticsLabel: 'MySalon Logo'),
                            ),
                            const Text(
                              'Reg??strate para acceder a miles de nuestros servicios de belleza en Espa??a.',
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  fontSize: 18,
                                  color: Color(0xFF8E8E8E)),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(top: 20.0),
                              child: OutlinedButton.icon(
                                style: OutlinedButton.styleFrom(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 10, vertical: 15),
                                  side: const BorderSide(
                                      color: Color(0xFF385185), width: 2),
                                  shape: const RoundedRectangleBorder(
                                      borderRadius:
                                          BorderRadius.all(Radius.circular(5))),
                                ),
                                label: const Text('Registrarse con Facebook',
                                    style: TextStyle(
                                        fontSize: 16,
                                        color: Color(0xFF385185))),
                                icon: SvgPicture.asset(
                                    'assets/images/icons/fb.svg',
                                    width: 28,
                                    semanticsLabel: 'FACEBOOK'),
                                onPressed: () {},
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(top: 20),
                              child: OutlinedButton.icon(
                                style: OutlinedButton.styleFrom(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 24, vertical: 15),
                                  side: const BorderSide(
                                      color: Color(0xFFD50032), width: 2),
                                  shape: const RoundedRectangleBorder(
                                      borderRadius:
                                          BorderRadius.all(Radius.circular(5))),
                                ),
                                label: const Text('Registrarse con Google',
                                    style: TextStyle(
                                        fontSize: 16,
                                        color: Color(0xFFD50032))),
                                icon: SvgPicture.asset(
                                    'assets/images/icons/google.svg',
                                    width: 28,
                                    semanticsLabel: 'GOOGLE'),
                                onPressed: () {},
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(top: 15.0),
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
                                  "???",
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
                                padding:
                                    const EdgeInsets.symmetric(vertical: 20),
                                child: InkWell(
                                    onTap: () {
                                      BlocProvider.of<ImagePickBloc>(context)
                                          .add(const SelectImageEvent(
                                              ImageSource.gallery));
                                    },
                                    child: state is ImagePickInitial
                                        ? Padding(
                                            padding: const EdgeInsets.only(
                                                left: 20.0),
                                            child: Image.asset(
                                              'assets/images/upload_picture.png',
                                              width: 120,
                                            ),
                                          )
                                        : ClipRRect(
                                            borderRadius:
                                                BorderRadius.circular(100),
                                            child: Image.file(
                                              File(state.pickedFile.path),
                                              fit: BoxFit.cover,
                                              height: 120,
                                              width: 120,
                                            ),
                                          ))),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                controller: _nameController,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Nombre y Apellidos",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null)
                                      ? 'Debe indicar su nombre completo'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                controller: _usernameController,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Nombre de usuario",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null || value.isEmpty)
                                      ? 'Debe indicar el nombre de usuario'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                controller: _emailController,
                                keyboardType: TextInputType.emailAddress,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Correo electr??nico",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null || !value.contains('@'))
                                      ? 'El email no es v??lido'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                maxLength: 9,
                                controller: _phoneController,
                                keyboardType: TextInputType.phone,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  counterText: "",
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Tel??fono",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null)
                                      ? 'El tel??fono no es v??lido'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 10, vertical: 10),
                                child: TextFormField(
                                  readOnly: true,
                                  controller: _dateController,
                                  decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.all(10),
                                    filled: true,
                                    fillColor: const Color(0xFFFAFAFA),
                                    hintText: "Fecha de Nacimiento",
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
                                  ),
                                  onTap: () async {
                                    DateTime? pickedDate = await showDatePicker(
                                        context: context,
                                        initialDate: DateTime.now().subtract(
                                            const Duration(days: 10 * 365)),
                                        firstDate: DateTime(1900),
                                        lastDate: DateTime.now().subtract(
                                            const Duration(days: 10 * 365)));

                                    if (pickedDate != null) {
                                      String formattedDate =
                                          DateFormat('dd-MM-yyyy')
                                              .format(pickedDate);
                                      setState(() {
                                        _dateController.text = formattedDate;
                                      });
                                    } else {
                                      print("Debe seleccionar una fecha");
                                    }
                                  },
                                  validator: (value) {
                                    if (value == null || value.isEmpty) {
                                      return 'Debe indicar su fecha de nacimiento';
                                    }
                                    return null;
                                  },
                                )),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                obscureText: true,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Contrase??a",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null || value.isEmpty)
                                      ? 'Debe escribir una contrase??a'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                obscureText: true,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Confirmar Contrase??a",
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
                                ),
                                onSaved: (String? value) {},
                                validator: (String? value) {
                                  return (value == null || value.isEmpty)
                                      ? 'Debe repetir su contrase??a'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                                padding:
                                    const EdgeInsets.symmetric(vertical: 20),
                                child: GestureDetector(
                                  onTap: () {
                                    if (_formKey.currentState!.validate()) {
                                      final registerDto = RegisterDto(
                                          name: _nameController.text,
                                          email: _emailController.text,
                                          phone: _phoneController.text,
                                          birthDate: _dateController.text,
                                          username: _usernameController.text,
                                          password: _passwordController.text,
                                          password2:
                                              _confirmPasswordController.text);
                                      BlocProvider.of<RegisterBloc>(context)
                                          .add(DoRegisterEvent(
                                              registerDto, avatarPath));
                                      Navigator.pushNamed(context, '/login');
                                    }
                                  },
                                  child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                      fixedSize: const Size(250, 50),
                                      primary: const Color(0xFFFF5A5F),
                                      onPrimary: Colors.white,
                                    ),
                                    onPressed: () {},
                                    child: GestureDetector(
                                      child: const Text('Siguiente',
                                          style: TextStyle(fontSize: 16)),
                                    ),
                                  ),
                                )),
                            const Padding(
                              padding: EdgeInsets.only(top: 20),
                              child: Text(
                                'Al registrarte, aceptas nuestras Condiciones.'
                                'Obt??n m??s informaci??n sobre c??mo recopilamos,'
                                ' usamos y compartimos tu informaci??n en la'
                                ' Pol??tica de datos, as?? como el uso que hacemos'
                                ' de las cookies y tecnolog??as similares en nuestra'
                                ' Pol??tica de cookies.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                    fontSize: 14, color: Color(0xFF8E8E8E)),
                              ),
                            )
                          ],
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    width: 500,
                    height: 100,
                    child: Card(
                      color: Colors.white,
                      semanticContainer: true,
                      clipBehavior: Clip.antiAliasWithSaveLayer,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0),
                      ),
                      elevation: 10,
                      child: Container(
                        padding: const EdgeInsets.all(0),
                        child: TextButton(
                            onPressed: () =>
                                Navigator.pushNamed(context, '/register'),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                const Text(
                                  '??Ya tienes una cuenta?',
                                  style: TextStyle(
                                      fontSize: 16, color: Color(0xFF262626)),
                                ),
                                TextButton(
                                    onPressed: () => Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                const LoginScreen())),
                                    child: const Text(
                                      'Entrar',
                                      style: TextStyle(
                                          fontSize: 16,
                                          color: Color(0xFFFF5A5F)),
                                    )),
                              ],
                            )),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
