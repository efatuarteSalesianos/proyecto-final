class CreateSiteDto {
  CreateSiteDto({
    required this.name,
    required this.description,
    required this.address,
    required this.city,
    required this.postalCode,
    required this.email,
    required this.phone,
    required this.web,
    required this.openingHour,
    required this.closingHour,
  });
  late final String name;
  late final String description;
  late final String address;
  late final String city;
  late final String postalCode;
  late final String email;
  late final String phone;
  late final String web;
  late final DateTime openingHour;
  late final DateTime closingHour;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['description'] = description;
    data['address'] = address;
    data['city'] = city;
    data['postalCode'] = postalCode;
    data['email'] = email;
    data['phone'] = phone;
    data['web'] = web;
    data['openingHour'] = openingHour;
    data['closingHour'] = closingHour;
    return data;
  }
}
