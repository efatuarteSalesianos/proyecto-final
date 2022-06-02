import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/ui/create_appointment_screen.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:url_launcher/url_launcher.dart';

class SiteDetailScreen extends StatefulWidget {
  const SiteDetailScreen({
    Key? key,
    required this.id,
  }) : super(key: key);

  final int id;

  @override
  _SiteDetailState createState() => _SiteDetailState(id: this.id);
}

class _SiteDetailState extends State<SiteDetailScreen> {
  _SiteDetailState({
    required this.id,
  });
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

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: ((context) => _siteBloc),
        child: BlocBuilder<SitesBloc, SitesState>(builder: (context, state) {
          if (state is SitesInitial) {
            return const Center(child: CircularProgressIndicator());
          } else if (state is SitesFetched) {
            return _createBody(context);
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        }));
  }

  _createBody(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
            height: MediaQuery.of(context).size.height,
            color: const Color(0xFFFF5A5F),
            padding: const EdgeInsets.symmetric(vertical: 90, horizontal: 20),
            child: BlocConsumer<SitesBloc, SitesState>(
                listenWhen: (context, state) {
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
              return Text('Ha ocurrido un error');
            })),
      ),
    );
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

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}
