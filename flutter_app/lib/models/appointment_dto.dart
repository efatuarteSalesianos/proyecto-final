class CreateAppointmentDto {
  CreateAppointmentDto({
    required this.date,
    required this.description,
  });
  late final String date;
  late final String description;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['date'] = date;
    data['description'] = description;
    return data;
  }
}
