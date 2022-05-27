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

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            shadowColor: const Color(0xFFFF5A5F),
            backgroundColor: const Color(0xFFFF5A5F),
            foregroundColor: const Color(0xFFFFFFFF),
            floating: true,
            pinned: true,
            snap: false,
            centerTitle: false,
            title: Text('MySalon'),
            actions: [
              IconButton(
                icon: Icon(Icons.exit_to_app),
                onPressed: () => {
                  PreferenceUtils.clear(),
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const LoginScreen()))
                },
              ),
            ],
            bottom: AppBar(
              backgroundColor: const Color(0xFFFF5A5F),
              title: Container(
                width: double.infinity,
                height: 40,
                color: Colors.white,
                child: const Center(
                  child: TextField(
                    cursorColor: Color(0xFFFF5A5F),
                    decoration: InputDecoration(
                        border: InputBorder.none,
                        hoverColor: Color(0xFFFF5A5F),
                        focusColor: Color(0xFFFF5A5F),
                        fillColor: Color(0xFFFF5A5F),
                        hintText: '¿Dónde quieres buscar?',
                        prefixIcon: Icon(
                          Icons.location_city_outlined,
                          color: Color(0xFF000000),
                        ),
                        suffixIcon: Icon(
                          Icons.search_rounded,
                          color: Color(0xFF000000),
                        )),
                  ),
                ),
              ),
            ),
          ),
          SliverList(
            delegate: SliverChildListDelegate([SiteCard()]),
          ),
        ],
      ),
    );
  }
}
