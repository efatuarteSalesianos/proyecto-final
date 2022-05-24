class SiteResponse {
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

  SiteResponse({
    required this.id,
    required this.name,
    required this.description,
    required this.address,
    required this.city,
    required this.postalCode,
    required this.email,
    required this.phone,
    required this.web,
    required this.rate,
    required this.scaledFileUrl,
    required this.likes,
    required this.originalFileUrl,
    required this.type,
  });
  SiteResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].toString();
    description = json['description'].toString();
    address = json['address'].toString();
    city = json['city'].toString();
    postalCode = json['postalCode'].toString();
    email = json['email'].toString();
    phone = json['phone'].toString();
    web = json['web'].toString();
    rate = json['rate'];
    scaledFileUrl = json['scaledFileUrl'].toString();
    likes = json['likes'].toInt();
    originalFileUrl = json['originalFileUrl'].toString();
    type = json['type'].toString();
  }
  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['description'] = description;
    data['address'] = address;
    data['city'] = city;
    data['postalCode'] = postalCode;
    data['email'] = email;
    data['phone'] = phone;
    data['web'] = web;
    data['rate'] = rate;
    data['scaledFileUrl'] = scaledFileUrl;
    data['likes'] = likes;
    data['originalFileUrl'] = originalFileUrl;
    data['type'] = type;
    return data;
  }
}
