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

class SiteFetchError extends SitesState {
  final String message;
  const SiteFetchError(this.message);

  @override
  List<Object> get props => [message];
}
