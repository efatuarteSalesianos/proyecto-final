import 'package:flutter/material.dart';
import 'package:shimmer/shimmer.dart';

class ShimmerPostsList extends StatelessWidget {
  const ShimmerPostsList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    int time = 800;
    return SizedBox(
      width: MediaQuery.of(context).size.width,
      child: Column(
        children: [
          Shimmer.fromColors(
            highlightColor: Colors.white30,
            baseColor: Colors.grey.shade300,
            period: Duration(milliseconds: time),
            child: Container(
                width: MediaQuery.of(context).size.width,
                height: 350,
                color: Colors.grey),
          ),
        ],
      ),
    );
  }
}
