class AppointmentResponse {
  AppointmentResponse({
    required this.cliente,
    required this.site,
    required this.date,
    required this.hour,
    required this.description,
    required this.status,
  });
  late final String cliente;
  late final String site;
  late final String date;
  late final String hour;
  late final String description;
  late final String status;

  AppointmentResponse.fromJson(Map<String, dynamic> json) {
    cliente = json['cliente'];
    site = json['site'];
    date = json['date'];
    hour = json['hour'];
    description = json['description'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['cliente'] = cliente;
    _data['site'] = site;
    _data['date'] = date;
    _data['hour'] = hour;
    _data['description'] = description;
    _data['status'] = status;
    return _data;
  }
}
