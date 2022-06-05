class CreateCommentDto {
  CreateCommentDto({
    required this.title,
    required this.description,
    required this.rate,
  });
  late final String title;
  late final String description;
  late final double rate;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['title'] = title;
    data['description'] = description;
    data['rate'] = rate;
    return data;
  }
}
