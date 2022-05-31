class CreateCommentDto {
  CreateCommentDto({
    required this.title,
    required this.description,
    required this.rate,
    required this.image,
  });
  late final String title;
  late final String description;
  late final double rate;
  late final String image;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['title'] = title;
    data['description'] = description;
    data['rate'] = rate;
    data['image'] = image;
    return data;
  }
}
