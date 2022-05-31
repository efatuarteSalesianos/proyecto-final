import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/models/site_type_response.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/ui/site_detail_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/site_list.dart';
import 'package:flutter_app/widgets/site_type_chip_widget.dart';
import 'package:url_launcher/url_launcher.dart';

class BookingsScreen extends StatefulWidget {
  const BookingsScreen({Key? key}) : super(key: key);

  @override
  _BookingsScreenState createState() => _BookingsScreenState();
}

class _BookingsScreenState extends State<BookingsScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        shadowColor: const Color(0xFFFF5A5F),
        backgroundColor: const Color(0xFFFF5A5F),
        foregroundColor: const Color(0xFFFFFFFF),
        title: Text('Mis Reservas'),
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
              child: const SiteList()),
        ],
      ),
    );
  }
}
