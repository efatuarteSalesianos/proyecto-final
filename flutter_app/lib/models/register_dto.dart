class RegisterDto {
  RegisterDto(
      {required this.name,
      required this.email,
      required this.phone,
      required this.birthDate,
      required this.username,
      required this.password,
      required this.password2});
  late final String name;
  late final String email;
  late final String phone;
  late final String birthDate;
  late final String username;
  late final String password;
  late final String password2;

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['name'] = name;
    _data['email'] = email;
    _data['phone'] = phone;
    _data['birthDate'] = birthDate;
    _data['username'] = username;
    _data['password'] = password;
    _data['password2'] = password2;
    return _data;
  }
}
