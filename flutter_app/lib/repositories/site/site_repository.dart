import 'package:flutter_app/models/site_response.dart';

abstract class SiteRepository {
  Future<List<SiteResponse>> fetchSites();
  Future<List<SiteResponse>> fetchSitesWithType(String type);
}
