class RegisterResponse {
  RegisterResponse({
    required this.name,
    required this.email,
    required this.phone,
    required this.birthDate,
    required this.username,
    required this.avatar,
    required this.rol,
  });
  late final String name;
  late final String email;
  late final String phone;
  late final DateTime birthDate;
  late final String username;
  late final String avatar;
  late final String rol;

  RegisterResponse.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    email = json['email'];
    phone = json['phone'];
    birthDate = json['birthDate'];
    username = json['username'];
    avatar = json['avatar'];
    rol = json['rol'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['name'] = name;
    _data['email'] = email;
    _data['phone'] = phone;
    _data['birthDate'] = birthDate;
    _data['username'] = username;
    _data['avatar'] = avatar;
    _data['rol'] = rol;
    return _data;
  }
}