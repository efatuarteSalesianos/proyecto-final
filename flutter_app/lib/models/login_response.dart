class LoginResponse {
  LoginResponse({
    //required this.id,
    required this.fullName,
    required this.username,
    required this.avatar,
    required this.rol,
    required this.token,
  });
  //late final int id;
  late final String fullName;
  late final String username;
  late final String avatar;
  late final String rol;
  late final String token;

  LoginResponse.fromJson(Map<String, dynamic> json) {
    //id = json['id'];
    fullName = json['fullName'];
    username = json['username'];
    avatar = json['avatar'];
    rol = json['rol'];
    token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    //_data['id'] = id;
    _data['fullName'] = fullName;
    _data['username'] = username;
    _data['avatar'] = avatar;
    _data['rol'] = rol;
    _data['token'] = token;
    return _data;
  }
}
