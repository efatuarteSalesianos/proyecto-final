import 'package:flutter/material.dart';
import 'package:flutter_app/models/site_response.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/video_player_widget.dart';
import 'package:video_player/video_player.dart';

class SingleGridPicture extends StatefulWidget {
  final SiteResponse? site;

  const SingleGridPicture({Key? key, this.site}) : super(key: key);

  @override
  State<SingleGridPicture> createState() => _SingleGridPictureState();
}

class _SingleGridPictureState extends State<SingleGridPicture> {
  late VideoPlayerController _videoController;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    _videoController = VideoPlayerController.network(widget.site!.scaledFileUrl)
      ..addListener(() => setState(() {}))
      ..setLooping(false)
      ..initialize().then((_) => _videoController.play());
  }

  @override
  void dispose() {
    super.dispose();
    _videoController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      splashColor: const Color(0xFFFD8D32),
      onTap: () {},
      child: widget.site!.scaledFileUrl
                  .substring(widget.site!.scaledFileUrl.lastIndexOf(".") + 1) ==
              "mp4"
          ? VideoPlayerWidget(
              controller: _videoController,
            )
          : Image.network(widget.site!.scaledFileUrl, fit: BoxFit.cover),
    );
  }
}
