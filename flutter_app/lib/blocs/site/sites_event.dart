part of 'sites_bloc.dart';

abstract class SitesEvent extends Equatable {
  const SitesEvent();

  @override
  List<Object> get props => [];
}

class FetchSites extends SitesEvent {
  const FetchSites();

  @override
  List<Object> get props => [];
}

class FetchSitesWithType extends SitesEvent {
  final String type;

  const FetchSitesWithType(this.type);

  @override
  List<Object> get props => [type];
}

class FetchSitesWithName extends SitesEvent {
  final String name;

  const FetchSitesWithName(this.name);

  @override
  List<Object> get props => [name];
}

class FetchSitesWithPostalCode extends SitesEvent {
  final String postalCode;

  const FetchSitesWithPostalCode(this.postalCode);

  @override
  List<Object> get props => [postalCode];
}

class FetchSitesWithCity extends SitesEvent {
  final String city;

  const FetchSitesWithCity(this.city);

  @override
  List<Object> get props => [city];
}

class FetchSitesWithRate extends SitesEvent {
  final double rate;

  const FetchSitesWithRate(this.rate);

  @override
  List<Object> get props => [rate];
}

class FetchFavouriteSites extends SitesEvent {
  const FetchFavouriteSites();

  @override
  List<Object> get props => [];
}
