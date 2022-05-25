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
    final data = <String, dynamic>{};
    data['name'] = name;
    data['email'] = email;
    data['phone'] = phone;
    data['birthDate'] = birthDate;
    data['username'] = username;
    data['password'] = password;
    data['password2'] = password2;
    return data;
  }
}
