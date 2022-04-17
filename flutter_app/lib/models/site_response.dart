class SiteResponse {
  late int id;
  late String title;
  late String description;
  late bool privatePost;
  late String scaledFileUrl;
  late int likes;
  late bool liked;
  late String userId;
  late String username;
  late String originalFileUrl;

  SiteResponse({
    required this.id,
    required this.title,
    required this.description,
    required this.privatePost,
    required this.scaledFileUrl,
    required this.likes,
    required this.liked,
    required this.userId,
    required this.username,
    required this.originalFileUrl,
  });
  SiteResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'].toInt();
    title = json['title'].toString();
    description = json['description'].toString();
    privatePost = json['privatePost'];
    scaledFileUrl = json['scaledFileUrl'].toString();
    likes = json['likes'].toInt();
    liked = json['liked'];
    userId = json['userId'].toString();
    username = json['username'].toString();
    originalFileUrl = json['originalFileUrl'].toString();
  }
  Map<String, dynamic> toJson() {
    final data = <String, dynamic>{};
    data['id'] = id;
    data['title'] = title;
    data['description'] = description;
    data['privatePost'] = privatePost;
    data['scaledFileUrl'] = scaledFileUrl;
    data['likes'] = likes;
    data['liked'] = liked;
    data['userId'] = userId;
    data['username'] = username;
    data['originalFileUrl'] = originalFileUrl;
    return data;
  }
}
