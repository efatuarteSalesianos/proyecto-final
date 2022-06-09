class CreateAppointmentDto {
  CreateAppointmentDto({
    required this.date,
    required this.hour,
    required this.description,
  });
  late final String date;
  late final String hour;
  late final String description;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['date'] = date;
    data['hour'] = hour;
    data['description'] = description;
    return data;
  }
}
