class SiteDetailResponse {
  late int id;
  late String name;
  late String description;
  late String address;
  late String city;
  late String postalCode;
  late String email;
  late String web;
  late String phone;
  late int totalComments;
  late double rate;
  late String scaledFileUrl;
  late String originalFileUrl;
  late int likes;
  late bool liked;
  late String type;
  late String propietario;

  SiteDetailResponse({
    required this.id,
    required this.name,
    required this.description,
    required this.address,
    required this.city,
    required this.postalCode,
    required this.email,
    required this.web,
    required this.phone,
    required this.totalComments,
    required this.rate,
    required this.scaledFileUrl,
    required this.originalFileUrl,
    required this.likes,
    required this.liked,
    required this.type,
    required this.propietario,
  });
  SiteDetailResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].toString();
    description = json['description'].toString();
    address = json['address'].toString();
    city = json['city'].toString();
    postalCode = json['postalCode'].toString();
    email = json['email'].toString();
    phone = json['phone'].toString();
    web = json['web'].toString();
    totalComments = json['totalComments'].toInt();
    rate = json['rate'];
    scaledFileUrl = json['scaledFileUrl'].toString();
    originalFileUrl = json['originalFileUrl'].toString();
    likes = json['likes'].toInt();
    liked = json['liked'];
    type = json['type'].toString();
    propietario = json['propietario'].toString();
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
    data['totalComments'] = totalComments;
    data['rate'] = rate;
    data['scaledFileUrl'] = scaledFileUrl;
    data['originalFileUrl'] = originalFileUrl;
    data['likes'] = likes;
    data['liked'] = liked;
    data['type'] = type;
    data['propietario'] = propietario;
    return data;
  }
}
