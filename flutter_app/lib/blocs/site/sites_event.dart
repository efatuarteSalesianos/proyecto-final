part of 'sites_bloc.dart';

abstract class SitesEvent extends Equatable {
  const SitesEvent();

  @override
  List<Object> get props => [];
}

class FetchSite extends SitesEvent {
  const FetchSite();

  @override
  List<Object> get props => [];
}

class FetchSiteWithType extends SitesEvent {
  final String type;

  const FetchSiteWithType(this.type);

  @override
  List<Object> get props => [type];
}

class FetchSiteWithName extends SitesEvent {
  final String name;

  const FetchSiteWithName(this.name);

  @override
  List<Object> get props => [name];
}

class FetchSiteWithPostalCode extends SitesEvent {
  final String postalCode;

  const FetchSiteWithPostalCode(this.postalCode);

  @override
  List<Object> get props => [postalCode];
}

class FetchSiteWithCity extends SitesEvent {
  final String city;

  const FetchSiteWithCity(this.city);

  @override
  List<Object> get props => [city];
}

class FetchSiteWithRate extends SitesEvent {
  final double rate;

  const FetchSiteWithRate(this.rate);

  @override
  List<Object> get props => [rate];
}
