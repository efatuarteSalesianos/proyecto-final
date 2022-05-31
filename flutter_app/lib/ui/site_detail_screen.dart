import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:url_launcher/url_launcher.dart';

class SiteDetailScreen extends StatefulWidget {
  const SiteDetailScreen({Key? key}) : super(key: key);

  @override
  _SiteDetailState createState() => _SiteDetailState();
}

class _SiteDetailState extends State<SiteDetailScreen> {
  _SiteDetailState();
  late int id;
  late Future<SiteDetailResponse> Site;
  late SiteRepository siteRepository;
  late SitesBloc _siteBloc;

  @override
  void initState() {
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SitesBloc(siteRepository)..add(const FetchSites());
  }

  Widget _siteDetailItem(SiteDetailResponse site) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFFFF5A5F),
        title: Text(site.name),
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            ClipRRect(
              child: Image(
                width: MediaQuery.of(context).size.width,
                image: NetworkImage(site.scaledFileUrl),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(bottom: 10.0),
                    child: Text(site.description,
                        style: const TextStyle(
                            fontSize: 21, fontWeight: FontWeight.bold)),
                  ),
                ],
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextButton(
                    onPressed: () => launch(
                        'https://www.google.com/maps/search/?api=1&query=${site.address}'),
                    child: Text(
                      site.address,
                      maxLines: 1,
                      softWrap: false,
                      overflow: TextOverflow.fade,
                      style: const TextStyle(fontSize: 18),
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
                openwhatsapp("+34${site.phone}");
              },
            ),
          ],
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
    );
  }

  void openwhatsapp(String phone) async {
    var whatsappURlAndroid = "whatsapp://send?phone=$phone&text=hola";
    var whatappURLIos =
        "https://wa.me/$phone?text=${Uri.parse("Hola! Me gustaría pedir información")}";
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

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          body: FutureBuilder<SiteDetailResponse>(
              future: Site,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return _siteDetailItem(snapshot.data!);
                } else if (snapshot.hasError) {
                  return Text('${snapshot.error}');
                }
                return const CircularProgressIndicator();
              })),
    );
  }
}
