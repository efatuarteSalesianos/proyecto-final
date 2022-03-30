import 'package:flutter/material.dart';
import 'package:flutter_app/ui/home_screen.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/ui/register_screen.dart';
import 'package:flutter_app/ui/splash_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/bottom_navbar.dart';

void main() {
  PreferenceUtils.init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MySalon',
      theme: ThemeData(primarySwatch: Colors.blue),
      initialRoute: '/',
      routes: {
        '/': (context) => const SplashScreen(),
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(),
        '/home': (context) => const HomeScreen(),
        '/menu': (context) => const BottomNavbarWidget(),
      },
    );
  }
}
