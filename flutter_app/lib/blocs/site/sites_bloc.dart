import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
part 'sites_event.dart';
part 'sites_state.dart';

class SiteBloc extends Bloc<SitesEvent, SitesState> {
  final SiteRepository siteRepository;

  SiteBloc(this.siteRepository) : super(SitesInitial()) {
    on<FetchSite>(_sitesFetched);
  }

  void _sitesFetched(FetchSite event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchSites();
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SiteFetchError(e.toString()));
    }
  }
}
