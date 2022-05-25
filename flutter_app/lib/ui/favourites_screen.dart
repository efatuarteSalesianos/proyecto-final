import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/models/site_type_response.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/ui/site_detail_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/site_card.dart';
import 'package:flutter_app/widgets/site_type_chip_widget.dart';
import 'package:url_launcher/url_launcher.dart';

void main() {
  runApp(const MaterialApp(
    debugShowCheckedModeBanner: false,
  ));
}

class FavouritesScreen extends StatefulWidget {
  const FavouritesScreen({Key? key}) : super(key: key);

  @override
  _FavouritesScreenState createState() => _FavouritesScreenState();
}

class _FavouritesScreenState extends State<FavouritesScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        shadowColor: const Color(0xFFFF5A5F),
        backgroundColor: const Color(0xFFFF5A5F),
        foregroundColor: const Color(0xFFFFFFFF),
        title: const Text('Mis Favoritos'),
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
      body: ListView(
        children: [
          SizedBox(
              height: MediaQuery.of(context).size.height,
              child: const SiteCard()),
        ],
      ),
    );
  }
}
