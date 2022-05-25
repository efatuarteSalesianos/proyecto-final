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
      appBar: AppBar(
        shadowColor: const Color(0xFFFF5A5F),
        backgroundColor: const Color(0xFFFF5A5F),
        foregroundColor: const Color(0xFFFFFFFF),
        title: Text('MySalon'),
        actions: [
          // Navigate to the Search Screen
          IconButton(
              onPressed: () => Navigator.of(context)
                  .push(MaterialPageRoute(builder: (_) => SearchPage())),
              icon: Icon(Icons.search))
        ],
      ),
    );
  }
}

// Search Page
class SearchPage extends StatelessWidget {
  const SearchPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          shadowColor: const Color(0xFFFF5A5F),
          backgroundColor: const Color(0xFFFF5A5F),
          foregroundColor: const Color(0xFFFFFFFF),
          // The search area here
          title: Container(
            width: double.infinity,
            height: 40,
            decoration: BoxDecoration(
                color: Colors.white, borderRadius: BorderRadius.circular(5)),
            child: Center(
              child: TextField(
                decoration: InputDecoration(
                    prefixIcon: Icon(Icons.search),
                    suffixIcon: IconButton(
                      icon: Icon(Icons.clear),
                      onPressed: () {
                        /* Clear the search field */
                      },
                    ),
                    hintText: '¿Dónde quieres buscar?',
                    border: InputBorder.none),
              ),
            ),
          )),
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
