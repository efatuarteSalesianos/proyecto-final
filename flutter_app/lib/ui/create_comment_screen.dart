import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/comment/comments_bloc.dart';
import 'package:flutter_app/blocs/image_pick/image_pick_bloc.dart';
import 'package:flutter_app/blocs/register/register_bloc.dart';
import 'package:flutter_app/models/comment_dto.dart';
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

class CreateCommentScreen extends StatefulWidget {
  final id;
  const CreateCommentScreen({Key? key, required this.id}) : super(key: key);

  @override
  _CreateCommentScreenState createState() => _CreateCommentScreenState();
}

class _CreateCommentScreenState extends State<CreateCommentScreen> {
  XFile? _imageFile;

  final _formKey = GlobalKey<FormState>();

  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();
  final TextEditingController _rateController = TextEditingController();

  late String avatarPath = PreferenceUtils.getString('AVATAR')!;

  set _setImageFile(XFile? value) {
    _imageFile = value ?? null;
  }

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFFFF5A5F),
        title: const Text('Nuevo comentario'),
      ),
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
              return createCommentForm(context, state);
            }),
      ),
    );
  }

  Widget createCommentForm(BuildContext context, state) {
    return MaterialApp(
      home: Container(
        decoration: const BoxDecoration(color: Color(0xFFFF5A5F)),
        child: ListView(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 15),
              child: Column(
                children: [
                  SizedBox(
                    height: MediaQuery.of(context).size.height * 0.8,
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
                                padding:
                                    const EdgeInsets.symmetric(vertical: 20),
                                child: InkWell(
                                    onTap: () {
                                      BlocProvider.of<ImagePickBloc>(context)
                                          .add(const SelectImageEvent(
                                              ImageSource.gallery));
                                    },
                                    child: state is ImagePickInitial
                                        ? Image.asset(
                                            'assets/images/upload_picture.png',
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
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                controller: _titleController,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Título del comentario",
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
                                      ? 'Debe indicar el título del comentario'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                controller: _descriptionController,
                                keyboardType: TextInputType.multiline,
                                maxLines: null,
                                style: const TextStyle(
                                  fontSize: 18,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500,
                                ),
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(10),
                                  filled: true,
                                  fillColor: const Color(0xFFFAFAFA),
                                  hintText: "Descripción del comentario",
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
                                      ? 'Debe indicar la descripción de su comentario'
                                      : null;
                                },
                              ),
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 10, vertical: 10),
                              child: TextFormField(
                                maxLength: 2,
                                controller: _rateController,
                                keyboardType: TextInputType.number,
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
                                  hintText: "Valoración",
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
                                      ? 'Debe indicar la valoración en su comentario'
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
                                      final createCommentDto = CreateCommentDto(
                                          title: _titleController.text,
                                          description:
                                              _descriptionController.text,
                                          rate: double.parse(
                                              _rateController.text));
                                      BlocProvider.of<CommentsBloc>(context)
                                          .add(CreateCommentEvent(widget.id,
                                              createCommentDto, avatarPath));
                                      Navigator.pushNamed(context, '/home');
                                    }
                                  },
                                  child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                      fixedSize: const Size(250, 50),
                                      primary: const Color(0xFFFF5A5F),
                                      onPrimary: Colors.white,
                                    ),
                                    onPressed: () {},
                                    child: GestureDetector(
                                      child: const Text('Comentar',
                                          style: TextStyle(fontSize: 16)),
                                    ),
                                  ),
                                )),
                          ],
                        ),
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
