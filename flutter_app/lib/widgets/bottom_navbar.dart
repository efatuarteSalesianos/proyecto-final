import 'package:flutter_app/ui/bookings_screen.dart';
import 'package:flutter_app/ui/favourites_screen.dart';
import 'package:flutter_app/ui/home_screen.dart';
import 'package:flutter_app/ui/profile_screen.dart';
import 'package:flutter_svg/svg.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const BottomNavbarWidget());
}

class BottomNavbarWidget extends StatelessWidget {
  const BottomNavbarWidget({Key? key}) : super(key: key);

// This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'BottomNavBar',
      theme: ThemeData(
        primaryColor: const Color(0xFFFFFFFF),
        splashColor: Colors.transparent,
        highlightColor: Colors.transparent,
        hoverColor: Colors.transparent,
      ),
      debugShowCheckedModeBanner: false,
      home: const BottomNavbarScreen(),
    );
  }
}

class BottomNavbarScreen extends StatefulWidget {
  final String? token;
  final String? username;
  const BottomNavbarScreen({Key? key, this.token, this.username})
      : super(key: key);

  @override
  _BottomNavbarScreenState createState() => _BottomNavbarScreenState();
}

class _BottomNavbarScreenState extends State<BottomNavbarScreen> {
  int pageIndex = 0;

  @override
  void initState() {
    super.initState();
  }

  final pages = [
    const HomeScreen(),
    const FavouritesScreen(),
    const HomeScreen(),
    const BookingsScreen(),
    const ProfileScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
      body: pages[pageIndex],
      bottomNavigationBar: buildMyNavBar(context),
    ));
  }

  Container buildMyNavBar(BuildContext context) {
    return Container(
      height: 65,
      decoration: const BoxDecoration(
        color: Colors.white,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          IconButton(
            enableFeedback: false,
            onPressed: () {
              setState(() {
                pageIndex = 0;
              });
            },
            icon: pageIndex == 0
                ? SvgPicture.asset('assets/images/icons/home_filled.svg',
                    width: 23, semanticsLabel: 'Home Filled')
                : SvgPicture.asset(
                    'assets/images/icons/home.svg',
                    width: 23,
                    semanticsLabel: 'Home',
                    color: Colors.grey,
                  ),
          ),
          IconButton(
            enableFeedback: false,
            onPressed: () {
              setState(() {
                pageIndex = 1;
              });
            },
            icon: pageIndex == 1
                ? SvgPicture.asset(
                    'assets/images/icons/reels.svg',
                    width: 23,
                    semanticsLabel: 'Favorites',
                    color: Colors.black,
                  )
                : SvgPicture.asset(
                    'assets/images/icons/reels.svg',
                    width: 23,
                    semanticsLabel: 'Favorites',
                    color: Colors.grey,
                  ),
          ),
          IconButton(
            enableFeedback: false,
            onPressed: () {
              setState(() {
                pageIndex = 2;
              });
            },
            icon: pageIndex == 2
                ? SvgPicture.asset(
                    'assets/images/icons/search.svg',
                    width: 23,
                    semanticsLabel: 'Search',
                    color: Colors.black,
                  )
                : SvgPicture.asset(
                    'assets/images/icons/search.svg',
                    width: 23,
                    semanticsLabel: 'Search',
                    color: Colors.grey,
                  ),
          ),
          IconButton(
            enableFeedback: false,
            onPressed: () {
              setState(() {
                pageIndex = 3;
              });
            },
            icon: pageIndex == 3
                ? SvgPicture.asset(
                    'assets/images/icons/shop.svg',
                    width: 23,
                    semanticsLabel: 'Shop',
                    color: Colors.black,
                  )
                : SvgPicture.asset(
                    'assets/images/icons/shop.svg',
                    width: 23,
                    semanticsLabel: 'Shop',
                    color: Colors.grey,
                  ),
          ),
          IconButton(
            enableFeedback: false,
            onPressed: () {
              setState(() {
                pageIndex = 4;
              });
            },
            icon: pageIndex == 4
                ? Container(
                    height: 32,
                    width: 32,
                    decoration: BoxDecoration(
                      borderRadius: const BorderRadius.all(Radius.circular(50)),
                      border: Border.all(
                        width: 2,
                        color: Colors.black,
                        style: BorderStyle.solid,
                      ),
                    ),
                    child: CircleAvatar(
                      radius: 31.0,
                      backgroundImage: widget.username == 'efatuarte'
                          ? const AssetImage("assets/images/profile.jpg")
                          : const AssetImage(
                              "assets/images/default_avatar.png"),
                      backgroundColor: Colors.transparent,
                    ),
                  )
                : CircleAvatar(
                    radius: 31.0,
                    backgroundImage: widget.username == 'efatuarte'
                        ? const AssetImage("assets/images/profile.jpg")
                        : const AssetImage("assets/images/default_avatar.png"),
                    backgroundColor: Colors.transparent,
                  ),
          ),
        ],
      ),
    );
  }
}
