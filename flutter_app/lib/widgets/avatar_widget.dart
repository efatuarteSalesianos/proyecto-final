import 'package:flutter/material.dart';

class SampleAvatar extends StatelessWidget {
  const SampleAvatar({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 80,
      height: 80,
      padding: const EdgeInsets.all(2),
      decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topRight,
            end: Alignment.bottomLeft,
            colors: [
              Color(0xFF9E2692),
              Color(0xFFFD8D32),
            ],
          ),
          shape: BoxShape.circle),
      child: Container(
        decoration: BoxDecoration(
            shape: BoxShape.circle,
            border: Border.all(color: Colors.white, width: 2)),
        child: ClipRRect(
            borderRadius: BorderRadius.circular(50),
            child: Image.asset('assets/images/profile.jpg')),
      ),
    );
  }
}
