import 'dart:ffi';

import 'package:flutter_app/models/create_site_dto.dart';
import 'package:flutter_app/models/site_detail_response.dart';
import 'package:flutter_app/models/site_response.dart';

abstract class SiteRepository {
  Future<List<SiteResponse>> fetchSites();
  Future<List<SiteResponse>> fetchSitesWithType(String type);
  Future<List<SiteResponse>> fetchSitesWithName(String name);
  Future<List<SiteResponse>> fetchSitesWithRateGreaterThan(int rate);
  Future<SiteResponse> fetchSiteById(Long id);
  Future<SiteDetailResponse> createSite(
      CreateSiteDto createSiteDto, String imagePath);
}
