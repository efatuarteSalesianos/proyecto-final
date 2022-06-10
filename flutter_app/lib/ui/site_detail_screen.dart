import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/ui/create_appointment_screen.dart';
import 'package:flutter_app/ui/site_comment_list_screen.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:url_launcher/url_launcher.dart';

class SiteDetailScreen extends StatefulWidget {
  final id;
  final name;
  final phone;
  const SiteDetailScreen(
      {Key? key, required this.id, required this.name, required this.phone})
      : super(key: key);

  @override
  _SiteDetailState createState() => _SiteDetailState();
}

class _SiteDetailState extends State<SiteDetailScreen> {
  _SiteDetailState();
  late Future<SiteDetailResponse> site;
  late SiteRepository siteRepository;
  late SitesBloc _siteBloc;

  @override
  void initState() {
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SitesBloc(siteRepository)..add(FetchSiteDetails(widget.id));
  }

  @override
  Widget build(BuildContext context) {
    String siteName = widget.name;
    String decodeSiteName =
        utf8.decode(latin1.encode(siteName), allowMalformed: true);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFFFF5A5F),
        title: Text(decodeSiteName),
      ),
      body: BlocProvider(
          create: ((context) => _siteBloc),
          child: BlocBuilder<SitesBloc, SitesState>(builder: (context, state) {
            if (state is SitesInitial) {
              return const Center(child: CircularProgressIndicator());
            } else if (state is SiteDetailsFetched) {
              return _createBody(context);
            } else {
              return const Center(child: CircularProgressIndicator());
            }
          })),
      floatingActionButton: SizedBox(
        height: 70.0,
        width: 70.0,
        child: FittedBox(
          child: FloatingActionButton(
            elevation: 10,
            backgroundColor: const Color(0xFF25D366),
            child: SvgPicture.asset('assets/images/icons/whatsapp.svg',
                width: 40, semanticsLabel: 'Whatsapp'),
            onPressed: () {
              openwhatsapp("+34${widget.phone}");
            },
          ),
        ),
      ),
    );
  }

  _createBody(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
          height: MediaQuery.of(context).size.height * 1.0,
          color: Colors.white,
          child:
              BlocConsumer<SitesBloc, SitesState>(listenWhen: (context, state) {
            return state is SiteDetailsFetched || state is SitesFetchError;
          }, listener: (context, state) {
            if (state is SiteDetailsFetched) {
            } else if (state is SitesFetchError) {
              _showSnackbar(context, state.message);
            }
          }, buildWhen: (context, state) {
            return state is SitesInitial || state is SitesFetchError;
          }, builder: (context, state) {
            if (state is SitesInitial) {
              return const Center(child: CircularProgressIndicator());
            } else if (state is SiteDetailsFetched) {
              return _siteDetailItem(state.site);
            } else if (state is SitesFetchError) {
              _showSnackbar(context, state.message);
            }
            return const Text('Ha ocurrido un error');
          })),
    );
  }

  Widget _siteDetailItem(SiteDetailResponse site) {
    String name = site.name;
    String decodeName = utf8.decode(latin1.encode(name), allowMalformed: true);
    String description = site.description;
    String decodeDescription =
        utf8.decode(latin1.encode(description), allowMalformed: true);
    String address = site.address;
    String decodeAddress =
        utf8.decode(latin1.encode(address), allowMalformed: true);
    return Column(
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
                padding:
                    const EdgeInsets.symmetric(vertical: 10.0, horizontal: 10),
                child: Text(decodeDescription,
                    style: const TextStyle(fontSize: 19)),
              ),
            ],
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  SvgPicture.asset('assets/images/icons/phone.svg',
                      width: 34, semanticsLabel: 'Phone'),
                  TextButton(
                      onPressed: () => launch('tel: +34${site.phone}'),
                      child: Text(
                        site.phone,
                        maxLines: 1,
                        softWrap: false,
                        overflow: TextOverflow.fade,
                        style: const TextStyle(
                          fontSize: 18,
                          color: Color(0xFF3366BB),
                        ),
                      )),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  SvgPicture.asset('assets/images/icons/mail.svg',
                      width: 35, semanticsLabel: 'Mail'),
                  TextButton(
                      onPressed: () => launch(
                          'mailto:${site.email}?subject=Consulta%20de%20${site.name}'),
                      child: Text(
                        site.email,
                        maxLines: 1,
                        softWrap: false,
                        overflow: TextOverflow.fade,
                        style: const TextStyle(
                          fontSize: 18,
                          color: Color(0xFF3366BB),
                        ),
                      )),
                ],
              ),
              Padding(
                padding: const EdgeInsets.only(left: 2.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    SvgPicture.asset('assets/images/icons/maps.svg',
                        width: 30, semanticsLabel: 'Google Maps'),
                    TextButton(
                        onPressed: () => launch(
                            'https://www.google.com/maps/search/?api=1&query=$decodeName, $decodeAddress, ${site.postalCode}, ${site.city}'),
                        child: Flexible(
                          child: SizedBox(
                            width: MediaQuery.of(context).size.width * 0.8,
                            child: Text(
                              '$decodeAddress, ${site.postalCode}, ${site.city}',
                              overflow: TextOverflow.ellipsis,
                              style: const TextStyle(
                                fontSize: 18,
                                color: Color(0xFF3366BB),
                              ),
                            ),
                          ),
                        )),
                  ],
                ),
              ),
              Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 10.0, horizontal: 10),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Text("Valoración: ",
                            style: TextStyle(
                                fontSize: 18, fontWeight: FontWeight.bold)),
                        Text(
                          site.rate.toString(),
                          style: const TextStyle(
                            fontSize: 18,
                            color: Color(0xFFFF5A5F),
                          ),
                        ),
                        const Padding(
                          padding: EdgeInsets.symmetric(horizontal: 2),
                          child: IconTheme(
                              data: IconThemeData(
                                color: Color(0xFFFF5A5F),
                                size: 22,
                              ),
                              child: Icon(Icons.star)),
                        ),
                      ],
                    ),
                    Padding(
                      padding: const EdgeInsets.only(left: 22.0),
                      child: TextButton(
                          onPressed: () => Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => CommentList(
                                    id: site.id,
                                  ),
                                ),
                              ),
                          child: const Text(
                            'Ver comentarios',
                            style: TextStyle(
                                fontSize: 18, color: Color(0xFFFF5A5F)),
                          )),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding:
                  const EdgeInsets.symmetric(vertical: 10.0, horizontal: 10),
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  primary: const Color(0xFFFF5A5F),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                ),
                onPressed: () => {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => CreateAppointmentScreen(
                          id: site.id,
                          name: site.name,
                          image: site.scaledFileUrl,
                        ),
                      ))
                },
                child: Container(
                  alignment: Alignment.center,
                  height: MediaQuery.of(context).size.height * 0.08,
                  width: MediaQuery.of(context).size.width * 0.8,
                  child: const Text(
                    "Comprobar Disponibilidad",
                    style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                        fontSize: 22.0),
                  ),
                ),
              ),
            ),
          ],
        )
      ],
    );
  }

  void openwhatsapp(String phone) async {
    var whatsappURlAndroid =
        "whatsapp://send?phone=$phone&text=Hola,%20quiero%20reservar%20una%20cita";
    var whatappURLIos =
        "https://wa.me/$phone?text=${Uri.parse("Hola! Me gustaría pedir información!")}";
    if (Platform.isIOS) {
      if (await canLaunch(whatappURLIos)) {
        await launch(whatappURLIos, forceSafariVC: false);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("No tiene WhatsApp instalado")));
      }
    } else {
      if (await canLaunch(whatsappURlAndroid)) {
        await launch(whatsappURlAndroid);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("No tiene WhatsApp instalado")));
      }
    }
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}
