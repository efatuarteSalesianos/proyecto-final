class CommentResponse {
  CommentResponse({
    required this.cliente,
    required this.site,
    required this.title,
    required this.description,
    required this.rate,
    required this.image,
  });
  late final String cliente;
  late final String site;
  late final String title;
  late final String description;
  late final double rate;
  late final String image;

  CommentResponse.fromJson(Map<String, dynamic> json) {
    cliente = json['cliente'];
    site = json['site'];
    title = json['title'];
    description = json['description'];
    rate = json['rate'];
    image = json['image'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['cliente'] = cliente;
    _data['site'] = site;
    _data['title'] = title;
    _data['description'] = description;
    _data['rate'] = rate;
    _data['image'] = image;
    return _data;
  }
}
