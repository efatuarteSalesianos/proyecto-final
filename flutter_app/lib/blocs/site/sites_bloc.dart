import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/repositories/site/site_repository.dart';
part 'sites_event.dart';
part 'sites_state.dart';

class SitesBloc extends Bloc<SitesEvent, SitesState> {
  final SiteRepository siteRepository;

  SitesBloc(this.siteRepository) : super(SitesInitial()) {
    on<FetchSites>(_sitesFetched);
    on<FetchSitesWithType>(_sitesFetchedWithType);
    on<FetchSitesWithName>(_sitesFetchedWithName);
    on<FetchSitesWithPostalCode>(_sitesFetchedWithPostalCode);
    on<FetchSitesWithCity>(_sitesFetchedWithCity);
    on<FetchSitesWithRate>(_sitesFetchedWithRate);
    on<FetchFavouriteSites>(_favouriteSitesFeteched);
    on<FetchSiteDetails>(_siteDetailsFetched);
    on<FetchLike>(_addLike);
  }

  void _sitesFetched(FetchSites event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchSites();
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _sitesFetchedWithType(
      FetchSitesWithType event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchSitesWithType(event.type);
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _sitesFetchedWithName(
      FetchSitesWithName event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchSitesWithName(event.name);
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _sitesFetchedWithPostalCode(
      FetchSitesWithPostalCode event, Emitter<SitesState> emit) async {
    try {
      final sites =
          await siteRepository.fetchSitesWithPostalCode(event.postalCode);
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _sitesFetchedWithCity(
      FetchSitesWithCity event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchSitesWithCity(event.city);
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _sitesFetchedWithRate(
      FetchSitesWithRate event, Emitter<SitesState> emit) async {
    try {
      final sites =
          await siteRepository.fetchSitesWithRateGreaterThan(event.rate);
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _favouriteSitesFeteched(
      FetchFavouriteSites event, Emitter<SitesState> emit) async {
    try {
      final sites = await siteRepository.fetchFavouriteSites();
      emit(SitesFetched(sites));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _siteDetailsFetched(
      FetchSiteDetails event, Emitter<SitesState> emit) async {
    try {
      final site = await siteRepository.fetchSiteDetails(event.id);
      emit(SiteDetailsFetched(site));
      return;
    } on Exception catch (e) {
      emit(SitesFetchError(e.toString()));
    }
  }

  void _addLike(FetchLike event, Emitter<SitesState> emit) async {
    try {
      emit(const SiteFetchLike());
      return;
    } on Exception catch (e) {
      emit(SiteFetchLikeError(e.toString()));
    }
  }

  void _removeLike(FetchLike event, Emitter<SitesState> emit) async {
    try {
      emit(const SiteFetchDislike());
      return;
    } on Exception catch (e) {
      emit(SiteFetchLikeError(e.toString()));
    }
  }
}
