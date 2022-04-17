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
