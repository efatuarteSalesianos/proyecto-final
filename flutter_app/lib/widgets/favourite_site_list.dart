import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/ui/site_detail_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/rating_bar_widget.dart';
import 'package:flutter_app/widgets/shimmer_picture.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:like_button/like_button.dart';
import 'package:flutter_app/widgets/error_widget.dart';

class FavouriteSiteList extends StatefulWidget {
  const FavouriteSiteList({Key? key}) : super(key: key);

  @override
  _FavouriteSiteListState createState() => _FavouriteSiteListState();
}

class _FavouriteSiteListState extends State<FavouriteSiteList> {
  late SiteRepository siteRepository;
  late SitesBloc _siteBloc;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SitesBloc(siteRepository)..add(const FetchFavouriteSites());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: ((context) => _siteBloc),
        child: BlocBuilder<SitesBloc, SitesState>(builder: (context, state) {
          if (state is SitesInitial) {
            return Center(child: CircularProgressIndicator());
          } else if (state is SitesFetched) {
            return _createSitesView(context, state.sites);
          } else {
            return Center(child: CircularProgressIndicator());
          }
        }));
  }

  Widget _createBody(BuildContext context) {
    return BlocBuilder<SitesBloc, SitesState>(
      bloc: _siteBloc,
      builder: (context, state) {
        if (state is SitesInitial) {
          return Column(
            children: const [
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
            ],
          );
        } else if (state is SitesFetched) {
          return _createSitesView(context, state.sites);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createSitesView(BuildContext context, List<SiteResponse> sites) {
    return ListView.builder(
      itemBuilder: (context, index) {
        return _siteItem(context, sites[index]);
      },
      padding: const EdgeInsets.symmetric(vertical: 5),
      scrollDirection: Axis.vertical,
      itemCount: sites.length,
    );
  }

  Widget _siteItem(BuildContext context, SiteResponse site) {
    String siteName = site.name;
    String decodeSiteName =
        utf8.decode(latin1.encode(siteName), allowMalformed: true);
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      height: MediaQuery.of(context).size.height * 0.213,
      child: Stack(
        children: [
          Positioned.fill(
            child: ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Image.network(site.scaledFileUrl),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
                height: 170,
                decoration: BoxDecoration(
                    borderRadius: const BorderRadius.all(Radius.circular(20)),
                    gradient: LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: [
                          Colors.black.withOpacity(0.8),
                          Colors.transparent
                        ]))),
          ),
          Positioned(
            top: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Row(
                children: [
                  Text(decodeSiteName,
                      style:
                          const TextStyle(color: Colors.white, fontSize: 20)),
                ],
              ),
            ),
          ),
          Positioned(
            top: 0,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: LikeButton(
                size: 30,
                circleColor: const CircleColor(
                    start: Color(0xFFFF989C), end: Color(0xFFFF5A5F)),
                bubblesColor: const BubblesColor(
                  dotPrimaryColor: Color(0xFFFF5A5F),
                  dotSecondaryColor: Color(0xFFF2B203),
                ),
                likeBuilder: (bool isLiked) {
                  return Icon(
                    Icons.favorite,
                    color: site.liked ? const Color(0xFFFF5A5F) : Colors.white,
                    size: 30,
                  );
                },
              ),
            ),
          ),
          Positioned(
            bottom: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: IconTheme(
                data: const IconThemeData(
                  color: Color(0xFFFF5A5F),
                  size: 23,
                ),
                child: RatingBarWidget(site.rate),
              ),
            ),
          )
        ],
      ),
    );
  }
}
