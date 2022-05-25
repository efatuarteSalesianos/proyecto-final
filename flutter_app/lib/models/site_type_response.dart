class SiteTypeResponse {
  late int id;
  late String name;
  late String description;
  late String address;
  late String city;
  late String postalCode;
  late String email;
  late String phone;
  late String web;
  late double rate;
  late String scaledFileUrl;
  late int likes;
  late String originalFileUrl;
  late String type;

  SiteTypeResponse({
    required this.id,
    required this.name,
  });
  SiteTypeResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].toString();
  }
  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    return data;
  }
}
