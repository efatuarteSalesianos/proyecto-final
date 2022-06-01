import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/ui/create_appointment_screen.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:url_launcher/url_launcher.dart';

class SiteDetailScreen extends StatefulWidget {
  const SiteDetailScreen({Key? key, required this.id}) : super(key: key);

  final int id;

  @override
  _SiteDetailState createState() => _SiteDetailState(id: this.id);
}

class _SiteDetailState extends State<SiteDetailScreen> {
  _SiteDetailState({required this.id});
  late int id;
  late Future<SiteDetailResponse> site;
  late SiteRepository siteRepository;
  late SitesBloc _siteBloc;

  @override
  void initState() {
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SitesBloc(siteRepository)..add(FetchSiteDetails(id));
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
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Padding(
                  padding: const EdgeInsets.symmetric(
                      vertical: 10.0, horizontal: 20.0),
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      primary: Colors.white,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                    ),
                    onPressed: () => {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  const CreateAppointmentScreen()))
                    },
                    child: Container(
                      alignment: Alignment.center,
                      height: 80.0,
                      child: const Text(
                        "Comprobar Disponibilidad",
                        style: TextStyle(
                            color: Color(0xFFFF5A5F),
                            fontWeight: FontWeight.bold,
                            fontSize: 30.0),
                      ),
                    ),
                  ),
                ),
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
    var whatsappURlAndroid =
        "whatsapp://send?phone=$phone&text=Hola,%20quiero%20reservar%20una%20cita";
    var whatappURLIos =
        "https://wa.me/$phone?text=${Uri.parse("Hola! Me gustaría pedir información!")}";
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
              future: site,
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
