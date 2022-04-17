import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoPlayerWidget extends StatefulWidget {
  final VideoPlayerController controller;

  const VideoPlayerWidget({Key? key, required this.controller})
      : super(key: key);

  @override
  State<VideoPlayerWidget> createState() => _VideoPlayerWidgetState();
}

class _VideoPlayerWidgetState extends State<VideoPlayerWidget> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) => widget.controller.value.isInitialized
      ? Stack(
          alignment: Alignment.center,
          children: [
            AspectRatio(
              aspectRatio: widget.controller.value.aspectRatio,
              child: VideoPlayer(widget.controller),
            ),
            Container(
              child: !widget.controller.value.isPlaying
                  ? const Icon(
                      Icons.play_arrow,
                      size: 200,
                      color: Colors.white54,
                    )
                  : const Icon(
                      Icons.play_arrow,
                      size: 200,
                      color: Colors.transparent,
                    ),
            )
          ],
        )
      : const CircularProgressIndicator();
}
