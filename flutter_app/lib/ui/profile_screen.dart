import 'package:flutter/material.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/profile_menu_widget.dart';
import 'package:flutter_app/widgets/profile_pic_widget.dart';
import "dart:convert";

class MyProfileScreen extends StatelessWidget {
  const MyProfileScreen({Key? key}) : super(key: key);

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
          SliverAppBar(
            floating: true,
            pinned: true,
            snap: false,
            centerTitle: false,
            title: const Text('Mi Perfil'),
            shadowColor: const Color(0xFFFF5A5F),
            backgroundColor: const Color(0xFFFF5A5F),
            foregroundColor: const Color(0xFFFFFFFF),
            actions: [
              IconButton(
                  icon: const Icon(Icons.exit_to_app),
                  onPressed: () => {
                        PreferenceUtils.clear(),
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const LoginScreen()))
                      }),
            ],
          ),
          SliverList(
            delegate:
                SliverChildBuilderDelegate((BuildContext context, int index) {
              return SingleChildScrollView(
                padding: EdgeInsets.symmetric(vertical: 20),
                child: Column(
                  children: [
                    ProfilePicWidget(),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 20),
                      child: Text(
                        latin1.decode(latin1.encode(decodeFullName)),
                        style: TextStyle(
                            fontSize: 22, fontWeight: FontWeight.w500),
                      ),
                    ),
                    SizedBox(height: 20),
                    ProfileMenuWidget(
                      text: "Mi Cuenta",
                      icon: "assets/images/icons/profile.svg",
                      press: () => {},
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
                      press: () {},
                    ),
                  ],
                ),
              );
            }, childCount: 1),
          ),
        ],
      ),
    );
  }
}
