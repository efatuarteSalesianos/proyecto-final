import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/image_pick/image_pick_bloc.dart';
import 'package:flutter_app/blocs/register/register_bloc.dart';
import 'package:flutter_app/models/register_dto.dart';
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
  bool _isPrivate = false;
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
  void initState() async {
    PreferenceUtils.init();
    _dateController.text = "";
    super.initState();
    ;
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
      home: Scaffold(
        body: Container(
          decoration: const BoxDecoration(color: Color(0xFFE1E1E1)),
          child: ListView(
            children: [
              Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 15, vertical: 15),
                child: Column(
                  children: [
                    SizedBox(
                      height: 1000,
                      child: Card(
                        color: Colors.white,
                        semanticContainer: true,
                        clipBehavior: Clip.antiAliasWithSaveLayer,
                        child: Container(
                          padding: const EdgeInsets.all(30),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Padding(
                                padding: const EdgeInsets.only(bottom: 5),
                                child: SvgPicture.asset(
                                    'assets/images/logo.svg',
                                    width: 200,
                                    semanticsLabel: 'Instagram Logo'),
                              ),
                              const Text(
                                'Regístrate para ver fotos y vídeos de tus amigos.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                    fontWeight: FontWeight.w600,
                                    fontSize: 20,
                                    color: Color(0xFF8E8E8E)),
                              ),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(vertical: 15),
                                child: ElevatedButton(
                                  style: ElevatedButton.styleFrom(
                                    fixedSize: const Size(275, 50),
                                    primary: const Color(0xFFFF5A5F),
                                    onPrimary: Colors.white,
                                  ),
                                  onPressed: () {
                                    Navigator.of(context).push(
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                const BottomNavbarScreen()));
                                  },
                                  child: GestureDetector(
                                    child: Row(
                                      children: [
                                        SvgPicture.asset(
                                            'assets/images/icons/fb_white.svg',
                                            width: 28,
                                            semanticsLabel: 'Facebook'),
                                        const Padding(
                                          padding: EdgeInsets.only(left: 5),
                                          child: Text(
                                              'Iniciar sesión con Facebook',
                                              style: TextStyle(fontSize: 16)),
                                        ),
                                      ],
                                    ),
                                  ),
                                ),
                              ),
                              Row(children: [
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
                              Padding(
                                  padding: const EdgeInsets.only(bottom: 10),
                                  child: InkWell(
                                      onTap: () {
                                        BlocProvider.of<ImagePickBloc>(context)
                                            .add(const SelectImageEvent(
                                                ImageSource.gallery));
                                      },
                                      child: state is ImagePickInitial
                                          ? Image.asset(
                                              'assets/images/upload_image.png',
                                              width: 110,
                                            )
                                          : ClipRRect(
                                              borderRadius:
                                                  BorderRadius.circular(100),
                                              child: Image.file(
                                                File(state.pickedFile.path),
                                                fit: BoxFit.cover,
                                                height: 110,
                                                width: 110,
                                              ),
                                            ))),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 10),
                                child: TextFormField(
                                  controller: _nameController,
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
                                    return (value == null ||
                                            !value.contains('@'))
                                        ? 'El nombre no es válido'
                                        : null;
                                  },
                                ),
                              ),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 10),
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
                                    hintText: "Correo electrónico",
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
                                    return (value == null ||
                                            !value.contains('@'))
                                        ? 'El email no es válido'
                                        : null;
                                  },
                                ),
                              ),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 10),
                                child: TextFormField(
                                  controller: _phoneController,
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
                                    hintText: "Teléfono",
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
                                    return (value == null ||
                                            !value.contains('@'))
                                        ? 'El teléfono no es válido'
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
                                      horizontal: 10),
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
                                            color: Color(0xFFF7F7F7),
                                            width: 1.0),
                                        borderRadius:
                                            BorderRadius.circular(6.0),
                                      ),
                                      focusedBorder: OutlineInputBorder(
                                        borderSide: const BorderSide(
                                            color: Colors.grey, width: 2.0),
                                        borderRadius:
                                            BorderRadius.circular(6.0),
                                      ),
                                    ),
                                    onTap: () async {
                                      DateTime? pickedDate =
                                          await showDatePicker(
                                              context: context,
                                              initialDate: DateTime.now()
                                                  .subtract(const Duration(
                                                      days: 10 * 365)),
                                              firstDate: DateTime(1900),
                                              lastDate: DateTime.now().subtract(
                                                  const Duration(
                                                      days: 10 * 365)));

                                      if (pickedDate != null) {
                                        String formattedDate =
                                            DateFormat('dd-MM-yyyy')
                                                .format(pickedDate);
                                        setState(() {
                                          _dateController.text = formattedDate;
                                        });
                                      } else {
                                        print("Date is not selected");
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
                                  ),
                                  onSaved: (String? value) {},
                                  validator: (String? value) {
                                    return (value == null || value.isEmpty)
                                        ? 'Debe escribir una contraseña'
                                        : null;
                                  },
                                ),
                              ),
                              Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 10),
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
                                    hintText: "Confirmar Contraseña",
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
                                        ? 'Debe repetir su contraseña'
                                        : null;
                                  },
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 10, vertical: 10),
                                child: Row(
                                  children: [
                                    const Text(
                                        '¿Desea hacer su cuenta privada?',
                                        style: TextStyle(
                                            fontSize: 15,
                                            color: Color(0xFF00376B))),
                                    Checkbox(
                                      value: _isPrivate,
                                      onChanged: (value) {
                                        setState(() {
                                          _isPrivate = !_isPrivate;
                                        });
                                      },
                                    ),
                                  ],
                                ),
                              ),
                              GestureDetector(
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
                                    BlocProvider.of<RegisterBloc>(context).add(
                                        DoRegisterEvent(
                                            registerDto, avatarPath));
                                    Navigator.pushNamed(context, '/login');
                                  }
                                },
                                child: ElevatedButton(
                                  style: ElevatedButton.styleFrom(
                                    fixedSize: const Size(275, 50),
                                    primary: const Color(0xFFFF5A5F),
                                    onPrimary: Colors.white,
                                  ),
                                  onPressed: () {},
                                  child: GestureDetector(
                                    child: const Text('Siguiente',
                                        style: TextStyle(fontSize: 16)),
                                  ),
                                ),
                              ),
                              const Padding(
                                padding: EdgeInsets.only(top: 20),
                                child: Text(
                                  'Al registrarte, aceptas nuestras Condiciones.'
                                  'Obtén más información sobre cómo recopilamos,'
                                  ' usamos y compartimos tu información en la'
                                  ' Política de datos, así como el uso que hacemos'
                                  ' de las cookies y tecnologías similares en nuestra'
                                  ' Política de cookies.',
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                      fontSize: 13, color: Color(0xFF8E8E8E)),
                                ),
                              )
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
                              onPressed: () =>
                                  Navigator.pushNamed(context, '/register'),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  const Text(
                                    '¿Tienes una cuenta?',
                                    style: TextStyle(
                                        fontSize: 16, color: Color(0xFF262626)),
                                  ),
                                  TextButton(
                                      onPressed: () => Navigator.pushNamed(
                                          context, '/login'),
                                      child: const Text(
                                        'Entrar',
                                        style: TextStyle(
                                            fontSize: 16,
                                            color: Color(0xFFFF5A5F)),
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
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
