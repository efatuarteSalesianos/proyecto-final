part of 'sites_bloc.dart';

abstract class SitesState extends Equatable {
  const SitesState();

  @override
  List<Object> get props => [];
}

class SitesInitial extends SitesState {}

class SitesFetched extends SitesState {
  final List<SiteResponse> sites;

  const SitesFetched(this.sites);

  @override
  List<Object> get props => [sites];
}

class SitesFetchedWithType extends SitesState {
  final List<SiteResponse> sites;
  final String type;

  const SitesFetchedWithType(this.sites, this.type);

  @override
  List<Object> get props => [sites, type];
}

class SitesFetchedWithName extends SitesState {
  final List<SiteResponse> sites;
  final String name;

  const SitesFetchedWithName(this.sites, this.name);

  @override
  List<Object> get props => [sites, name];
}

class SitesFetchedWithPostalCode extends SitesState {
  final List<SiteResponse> sites;
  final String postalCode;

  const SitesFetchedWithPostalCode(this.sites, this.postalCode);

  @override
  List<Object> get props => [sites, postalCode];
}

class SitesFetchedWithCity extends SitesState {
  final List<SiteResponse> sites;
  final String city;

  const SitesFetchedWithCity(this.sites, this.city);

  @override
  List<Object> get props => [sites, city];
}

class SitesFetchedWithRate extends SitesState {
  final List<SiteResponse> sites;
  final double rate;

  const SitesFetchedWithRate(this.sites, this.rate);

  @override
  List<Object> get props => [sites, rate];
}

class FavouriteSitesFetched extends SitesState {
  final List<SiteResponse> sites;

  const FavouriteSitesFetched(this.sites);

  @override
  List<Object> get props => [sites];
}

class SiteDetailsFetched extends SitesState {
  final SiteDetailResponse site;

  const SiteDetailsFetched(this.site);

  @override
  List<Object> get props => [];
}

class SitesFetchError extends SitesState {
  final String message;
  const SitesFetchError(this.message);

  @override
  List<Object> get props => [message];
}
