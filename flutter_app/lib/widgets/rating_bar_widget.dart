import 'package:flutter/material.dart';

class RatingBarWidget extends StatelessWidget {
  final double value;
  const RatingBarWidget(double rate) : value = rate;

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: List.generate(5, (index) {
        return Icon(
          index < value ? Icons.star : Icons.star_border,
        );
      }),
    );
  }
}
