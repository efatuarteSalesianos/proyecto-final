import 'package:flutter/material.dart';
import 'package:flutter_app/models/site_type_response.dart';

class SiteTypeChipWidget extends StatelessWidget {
  final SiteTypeResponse? siteType;

  const SiteTypeChipWidget({Key? key, this.siteType}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Chip(
      label: Text(
        siteType!.name,
        style: TextStyle(color: Colors.white),
      ),
      backgroundColor: Color(0xFF0FF5A5F),
    );
  }
}
