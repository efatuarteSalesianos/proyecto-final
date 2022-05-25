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
          primarySwatch: Colors.red,
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
        title: const Text('Peluquería Mayte del Valle'),
      ),
      body: SingleChildScrollView(
        child: Column(
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
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: Column(
                children: const [
                  Padding(
                    padding: EdgeInsets.only(bottom: 10.0),
                    child: Text('Tu Peluquería en Sevilla',
                        style: TextStyle(
                            fontSize: 21, fontWeight: FontWeight.bold)),
                  ),
                  Text(
                    'En el Salón de Peluquería y Belleza MdV estamos especializados en asesorarte para el cuidado integral de tu imagen. Somos capaces de sacar lo más bello que hay en ti, a través de tu propia imagen sin dejar de ser tú misma.',
                    style: TextStyle(fontSize: 17),
                    textAlign: TextAlign.justify,
                  ),
                  Text(
                    'En el mundo de la imagen desde 1986, Mayte del Valle abrió su primer salón de peluquería en Sevilla, su ciudad natal. Desde entonces se ha dedicado a este arte, en continuo reciclaje, asistiendo anualmente a diversos ateliers, seminarios y cursos, evaluando nuevas tendencias en el mundo de la moda en ciudades como París, Londres o Milán.',
                    style: TextStyle(fontSize: 17),
                    textAlign: TextAlign.justify,
                  )
                ],
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextButton(
                    onPressed: () => launch(
                        'https://www.google.com/maps/search/?api=1&query=Mayte+del+Valle+Calle+san+Jacinto+68+Sevilla'),
                    child: const Text(
                      'C. San Jacinto, 68',
                      maxLines: 1,
                      softWrap: false,
                      overflow: TextOverflow.fade,
                      style: TextStyle(fontSize: 18),
                    )),
                SvgPicture.asset('assets/images/icons/maps.svg',
                    width: 30, semanticsLabel: 'Google Maps')
              ],
            ),
            FloatingActionButton(
              elevation: 50,
              backgroundColor: const Color(0xFF25D366),
              child: SvgPicture.asset('assets/images/icons/whatsapp.svg',
                  width: 50, semanticsLabel: 'Whatsapp'),
              onPressed: () {
                openwhatsapp("+34638924930");
              },
            ),
          ],
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
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
