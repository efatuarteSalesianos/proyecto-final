import 'package:flutter/material.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/widgets/rating_bar_widget.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:like_button/like_button.dart';

class SiteCard extends StatelessWidget {
  final SiteResponse? site;

  const SiteCard({Key? key, this.site}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      height: MediaQuery.of(context).size.height * 0.213,
      child: Stack(
        children: [
          Positioned.fill(
            child: ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Image.network(site!.scaledFileUrl, fit: BoxFit.cover),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
                height: 170,
                decoration: BoxDecoration(
                    borderRadius: const BorderRadius.all(Radius.circular(20)),
                    gradient: LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: [
                          Colors.black.withOpacity(0.8),
                          Colors.transparent
                        ]))),
          ),
          Positioned(
            top: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Row(
                children: [
                  Text(site!.name,
                      style:
                          const TextStyle(color: Colors.white, fontSize: 20)),
                ],
              ),
            ),
          ),
          Positioned(
            top: 0,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: LikeButton(
                size: 30,
                circleColor: const CircleColor(
                    start: Color(0xFFFF989C), end: Color(0xFFFF5A5F)),
                bubblesColor: const BubblesColor(
                  dotPrimaryColor: Color(0xFFFF5A5F),
                  dotSecondaryColor: Color(0xFFF2B203),
                ),
                likeBuilder: (bool isLiked) {
                  return Icon(
                    Icons.favorite,
                    color: isLiked ? const Color(0xFFFF5A5F) : Colors.white,
                    size: 30,
                  );
                },
              ),
            ),
          ),
          Positioned(
            bottom: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: IconTheme(
                data: const IconThemeData(
                  color: Color(0xFFFF5A5F),
                  size: 23,
                ),
                child: RatingBarWidget(site!.rate),
              ),
            ),
          )
        ],
      ),
    );
  }
}
