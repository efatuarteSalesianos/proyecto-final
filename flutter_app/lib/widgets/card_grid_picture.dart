import 'package:flutter/material.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/single_card_picture.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CardGridPicture extends StatefulWidget {
  const CardGridPicture({Key? key}) : super(key: key);

  @override
  _CardGridPictureState createState() => _CardGridPictureState();
}

class _CardGridPictureState extends State<CardGridPicture> {
  late SiteRepository siteRepository;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    siteRepository = SiteRepositoryImpl();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: RefreshIndicator(
            onRefresh: () async {},
            child: SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                child: _createBody(context))));
  }

  Widget _createBody(BuildContext context) {
    return Text('Peinado');
  }

  Widget _createPicturesView(BuildContext context, List<SiteResponse> sites) {
    return Column(
      children: [
        SizedBox(
          height: MediaQuery.of(context).size.height,
          child: GridView.builder(
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 3,
              mainAxisSpacing: 0,
              crossAxisSpacing: 2,
            ),
            itemCount: sites.length,
            itemBuilder: (BuildContext context, int index) {
              int reverseIndex = sites.length - 1 - index;
              return SingleGridPicture(site: sites[reverseIndex]);
            },
            padding: const EdgeInsets.symmetric(vertical: 10),
            scrollDirection: Axis.vertical,
          ),
        ),
      ],
    );
  }
}
