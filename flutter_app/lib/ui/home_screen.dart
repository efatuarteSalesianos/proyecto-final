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
  List<dynamic> sites = [];
  List<dynamic> siteTypes = [];

  Future<void> readJson() async {
    final String response = await rootBundle.loadString('assets/sites.json');
    final data = await json.decode(response);

    final String responseTypes =
        await rootBundle.loadString('assets/site_types.json');
    final dataTypes = await json.decode(responseTypes);

    setState(() {
      sites = data['sites'].map((data) => SiteResponse.fromJson(data)).toList();
      siteTypes = data['siteTypes']
          .map((dataTypes) => SiteTypeResponse.fromJson(dataTypes))
          .toList();
    });
  }

  @override
  void initState() {
    super.initState();
    readJson();
  }

  @override
  Widget build(BuildContext context) {
    return ListView(children: [
      Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
            child: SizedBox(
              width: MediaQuery.of(context).size.width,
              height: 50,
              child: ListView.builder(
                scrollDirection: Axis.horizontal,
                itemCount: siteTypes.length,
                itemBuilder: (context, index) {
                  return SiteTypeChipWidget(siteType: siteTypes[index]);
                },
              ),
            ),
          ),
          SizedBox(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.height,
            child: ListView.builder(
              scrollDirection: Axis.vertical,
              itemCount: sites.length,
              itemBuilder: (context, index) {
                return SiteCard(
                  site: sites[index],
                );
              },
            ),
          ),
        ],
      )
    ]);
  }
}
