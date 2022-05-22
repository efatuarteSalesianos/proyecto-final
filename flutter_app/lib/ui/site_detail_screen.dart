import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:url_launcher/url_launcher.dart';

class MySiteDetailScreen extends StatelessWidget {
  const MySiteDetailScreen({Key? key, required this.site}) : super(key: key);

  final SiteResponse site;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        // Remove the debug banner
        debugShowCheckedModeBanner: false,
        title: 'Site Details',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const SiteDetailScreen());
  }
}

class SiteDetailScreen extends StatefulWidget {
  const SiteDetailScreen({Key? key}) : super(key: key);

  @override
  _SiteDetailScreenState createState() => _SiteDetailScreenState();
}

class _SiteDetailScreenState extends State<SiteDetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFFFF5A5F),
        title: const Text('Site Details'),
      ),
      body: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ClipRRect(
              child: Image(
                width: MediaQuery.of(context).size.width,
                image: const NetworkImage(
                    'https://phantom-elmundo.unidadeditorial.es/37812441ebf2e1d7b564b23077108513/resize/640/assets/multimedia/imagenes/2021/11/17/16371506566138.png'),
              ),
            ),
            Flexible(
                child: Row(
              children: [
                TextButton(
                    onPressed: () => launch(
                        'https://www.google.com/maps/search/?api=1&query=Mayte+del+Valle+Calle+san+Jacinto+68+Sevilla'),
                    child: const Text(
                      'C. San Jacinto, 68',
                      maxLines: 1,
                      softWrap: false,
                      overflow: TextOverflow.fade,
                      style: TextStyle(fontSize: 16),
                    )),
                SvgPicture.asset('assets/images/icons/maps.svg',
                    width: 30, semanticsLabel: 'Google Maps')
              ],
            )),
            const Flexible(
                child: Text(
              'Mayte del Valle',
              maxLines: 1,
              softWrap: true,
              overflow: TextOverflow.fade,
              style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
            )),
            Flexible(
                child: Row(children: [
              GestureDetector(
                onTap: () {
                  openwhatsapp("+34638924930");
                },
                child: const Text(
                  '638 92 49 30',
                  maxLines: 1,
                  softWrap: false,
                  overflow: TextOverflow.fade,
                  style: TextStyle(fontSize: 18, color: Colors.black),
                ),
              ),
              SvgPicture.asset('assets/images/icons/whatsapp.svg',
                  width: 30, semanticsLabel: 'Whatsapp')
            ])),
          ]),
    );
  }

  void openwhatsapp(String phone) async {
    var whatsappURlAndroid = "whatsapp://send?phone=$phone&text=hello";
    var whatappURLIos = "https://wa.me/$phone?text=${Uri.parse("hello")}";
    if (Platform.isIOS) {
      // for iOS phone only
      if (await canLaunch(whatappURLIos)) {
        await launch(whatappURLIos, forceSafariVC: false);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("No tiene WhatsApp instalado")));
      }
    } else {
      // android , web
      if (await canLaunch(whatsappURlAndroid)) {
        await launch(whatsappURlAndroid);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("No tiene WhatsApp instalado")));
      }
    }
  }
}
