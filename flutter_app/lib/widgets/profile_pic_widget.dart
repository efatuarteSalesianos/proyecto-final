import 'package:flutter/material.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';

class ProfilePicWidget extends StatelessWidget {
  const ProfilePicWidget({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    String avatar = PreferenceUtils.getString('AVATAR')!;

    return SizedBox(
      height: 115,
      width: 115,
      child: Stack(
        fit: StackFit.expand,
        clipBehavior: Clip.none,
        children: [
          CircleAvatar(
            backgroundImage: NetworkImage(avatar),
          ),
        ],
      ),
    );
  }
}
