import 'package:flutter/material.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/ui/my_profile_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/profile_menu_widget.dart';
import 'package:flutter_app/widgets/profile_pic_widget.dart';
import "dart:convert";

class ProfileMenuScreen extends StatelessWidget {
  const ProfileMenuScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        // Remove the debug banner
        debugShowCheckedModeBanner: false,
        title: 'Mi Perfil',
        theme: ThemeData(
          primarySwatch: Colors.red,
        ),
        home: const ProfileScreen());
  }
}

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  @override
  Widget build(BuildContext context) {
    String fullName = PreferenceUtils.getString('FULLNAME')!;
    String decodeFullName =
        utf8.decode(latin1.encode(fullName), allowMalformed: true);
    return Scaffold(
      body: CustomScrollView(
        slivers: [
          const SliverAppBar(
            floating: true,
            pinned: true,
            snap: false,
            centerTitle: false,
            title: Text('Mi Perfil'),
            shadowColor: Color(0xFFFF5A5F),
            backgroundColor: Color(0xFFFF5A5F),
            foregroundColor: Color(0xFFFFFFFF),
          ),
          SliverList(
            delegate:
                SliverChildBuilderDelegate((BuildContext context, int index) {
              return SingleChildScrollView(
                padding: const EdgeInsets.symmetric(vertical: 20),
                child: Column(
                  children: [
                    const ProfilePicWidget(),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20),
                      child: Text(
                        latin1.decode(latin1.encode(decodeFullName)),
                        style: const TextStyle(
                            fontSize: 22, fontWeight: FontWeight.w500),
                      ),
                    ),
                    const SizedBox(height: 20),
                    ProfileMenuWidget(
                      text: "Mi Cuenta",
                      icon: "assets/images/icons/profile.svg",
                      press: () => {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => MyProfileScreen()))
                      },
                    ),
                    ProfileMenuWidget(
                      text: "Notificaciones",
                      icon: "assets/images/icons/notification.svg",
                      press: () {},
                    ),
                    ProfileMenuWidget(
                      text: "Ajustes",
                      icon: "assets/images/icons/settings.svg",
                      press: () {},
                    ),
                    ProfileMenuWidget(
                      text: "Centro de Ayuda",
                      icon: "assets/images/icons/question.svg",
                      press: () {},
                    ),
                    ProfileMenuWidget(
                      text: "Salir",
                      icon: "assets/images/icons/exit.svg",
                      press: () => {
                        PreferenceUtils.clear(),
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const LoginScreen()))
                      },
                    ),
                    ProfileMenuWidget(
                        text: "Eliminar Cuenta",
                        icon: "assets/images/icons/trash.svg",
                        press: () => _delete(context)),
                  ],
                ),
              );
            }, childCount: 1),
          ),
        ],
      ),
    );
  }

  void _delete(BuildContext context) {
    showDialog(
        context: context,
        builder: (BuildContext ctx) {
          return AlertDialog(
            title: const Text('Confirmar eliminación'),
            content: const Text('¿Está seguro que desea eliminar su cuenta?'),
            actions: [
              TextButton(
                  onPressed: () {
                    setState(() {});

                    Navigator.of(context).pop();
                  },
                  child: const Text('Si')),
              TextButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: const Text('No'))
            ],
          );
        });
  }
}
