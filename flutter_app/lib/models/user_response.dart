class UserResponse {
  late String fullName;
  late String username;
  late String email;
  late String avatar;
  late DateTime birthDate;
  late String phone;
  late String rol;

  UserResponse({
    required this.fullName,
    required this.username,
    required this.email,
    required this.avatar,
    required this.birthDate,
    required this.phone,
    required this.rol,
  });
  UserResponse.fromJson(Map<String, dynamic> json) {
    fullName = json['fullName'].toString();
    username = json['username'].toString();
    email = json['email'].toString();
    avatar = json['avatar'].toString();
    birthDate = DateTime.parse(json['birthDate']);
    phone = json['phone'].toString();
    rol = json['rol'].toString();
  }
  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['fullName'] = fullName;
    data['username'] = username;
    data['email'] = email;
    data['avatar'] = avatar;
    data['birthDate'] = birthDate.toIso8601String();
    data['phone'] = phone;
    data['rol'] = rol;
    return data;
  }
}
