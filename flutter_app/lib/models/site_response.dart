import 'dart:ffi';

class SiteResponse {
  late Long id;
  late String name;
  late String address;
  late String city;
  late String postalCode;
  late int totalComments;
  late double rate;
  late String scaledFileUrl;
  late bool liked;

  SiteResponse({
    required this.id,
    required this.name,
    required this.address,
    required this.city,
    required this.postalCode,
    required this.totalComments,
    required this.rate,
    required this.scaledFileUrl,
    required this.liked,
  });
  SiteResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].toString();
    address = json['address'].toString();
    city = json['city'].toString();
    postalCode = json['postalCode'].toString();
    totalComments = json['totalComments'].toInt();
    rate = json['rate'];
    scaledFileUrl = json['scaledFileUrl'].toString();
    liked = json['liked'];
  }
  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['address'] = address;
    data['city'] = city;
    data['postalCode'] = postalCode;
    data['totalComments'] = totalComments;
    data['rate'] = rate;
    data['scaledFileUrl'] = scaledFileUrl;
    data['liked'] = liked;
    return data;
  }
}
