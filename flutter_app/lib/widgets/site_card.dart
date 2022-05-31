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

class SiteCard extends StatefulWidget {
  const SiteCard({Key? key}) : super(key: key);

  @override
  _SiteCardState createState() => _SiteCardState();
}

class _SiteCardState extends State<SiteCard> {
  late SiteRepository siteRepository;
  late SitesBloc _siteBloc;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SitesBloc(siteRepository)..add(const FetchSite());
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
    Route _createRoute() {
      return PageRouteBuilder(
        pageBuilder: (context, animation, secondaryAnimation) =>
            SiteDetailScreen(),
        transitionsBuilder: (context, animation, secondaryAnimation, child) {
          const begin = Offset(0, 2000.0);
          const end = Offset.infinite;
          const curve = Curves.easeOut;

          var tween =
              Tween(begin: begin, end: end).chain(CurveTween(curve: curve));

          return SlideTransition(
            position: animation.drive(tween),
            child: child,
          );
        },
      );
    }

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
                  Text(site.name,
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
