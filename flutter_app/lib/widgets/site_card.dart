import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/site/sites_bloc.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
import 'package:flutter_app/repositories/site/site_repository_impl.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/rating_bar_widget.dart';
import 'package:flutter_app/widgets/shimmer_picture.dart';
import 'package:flutter_app/widgets/single_site_widget.dart';
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
  late SiteBloc _siteBloc;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    siteRepository = SiteRepositoryImpl();
    _siteBloc = SiteBloc(siteRepository)..add(const FetchSite());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
        providers: [
          BlocProvider(create: (context) => _siteBloc),
        ],
        child: RefreshIndicator(
            onRefresh: () async {
              _siteBloc.add(const FetchSite());
            },
            child: SingleChildScrollView(
                physics: const AlwaysScrollableScrollPhysics(),
                child: SizedBox(
                    height: MediaQuery.of(context).size.height,
                    child: _createBody(context)))));
  }

  Widget _createBody(BuildContext context) {
    return BlocBuilder<SiteBloc, SitesState>(
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
        return SingleSite(site: sites[index]);
      },
      padding: const EdgeInsets.symmetric(vertical: 5),
      scrollDirection: Axis.vertical,
      itemCount: sites.length,
    );
  }
}
