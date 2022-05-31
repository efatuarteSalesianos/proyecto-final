import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';

abstract class SiteRepository {
  Future<List<SiteResponse>> fetchSites();
  Future<SiteDetailResponse> fetchSiteDetail(int id);
  Future<List<SiteResponse>> fetchSitesWithType(String type);
  Future<List<SiteResponse>> fetchSitesWithName(String name);
  Future<List<SiteResponse>> fetchSitesWithPostalCode(String postalCode);
  Future<List<SiteResponse>> fetchSitesWithCity(String city);
  Future<List<SiteResponse>> fetchSitesWithRateGreaterThan(double rate);
  Future<List<SiteResponse>> fetchFavouriteSites();
}
