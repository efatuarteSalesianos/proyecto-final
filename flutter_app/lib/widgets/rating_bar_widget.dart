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
          index + 1 <= value
              ? Icons.star
              : value > index + 0.33 && value < index + 1 - 0.33
                  ? Icons.star_half
                  : Icons.star_border,
        );
      }),
    );
  }
}
