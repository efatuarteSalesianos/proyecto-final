import 'package:flutter/material.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/avatar_widget.dart';
import 'package:flutter_app/widgets/card_grid_picture.dart';
import 'package:flutter_svg/flutter_svg.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  _MyProfileScreenState createState() => _MyProfileScreenState();
}

class _MyProfileScreenState extends State<ProfileScreen>
    with SingleTickerProviderStateMixin {
  late TabController _controller;
  int selectedIndex = 0;

  String username = PreferenceUtils.getString('USERNAME')!;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    _controller = TabController(initialIndex: 0, length: 4, vsync: this);
    _controller.addListener(() {
      setState(() {
        selectedIndex = _controller.index;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            body: ListView(children: [
      Padding(
        padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                SvgPicture.asset(
                  'assets/images/icon/lock.svg',
                  width: 18,
                  semanticsLabel: 'Private Account',
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 6),
                  child: Text(
                    username,
                    style: const TextStyle(
                        fontSize: 19, fontWeight: FontWeight.w600),
                  ),
                ),
                SvgPicture.asset(
                  'assets/images/icon/arrow.svg',
                  width: 12,
                  semanticsLabel: 'See more',
                  color: const Color(0xFF808080),
                ),
              ],
            ),
            Padding(
              padding: const EdgeInsets.only(left: 150.0),
              child: Row(mainAxisAlignment: MainAxisAlignment.start, children: [
                IconButton(
                  icon: SvgPicture.asset('assets/images/icon/add.svg',
                      width: 28, semanticsLabel: 'Add Site'),
                  onPressed: () {},
                ),
                IconButton(
                    icon: SvgPicture.asset('assets/images/icon/logout.svg',
                        width: 22, semanticsLabel: 'Logout'),
                    onPressed: () => {
                          PreferenceUtils.clear(),
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const LoginScreen()))
                        }),
              ]),
            ),
          ],
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(top: 10.0),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: Row(mainAxisAlignment: MainAxisAlignment.start, children: [
            Container(
              width: 90,
              height: 90,
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
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10),
              child: Padding(
                padding: const EdgeInsets.only(left: 10),
                child: SizedBox(
                  width: 80,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: const [
                      Text(
                        '280',
                        style: TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                      ),
                      Text(
                        'Publicaciones',
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                            fontSize: 15, fontWeight: FontWeight.w400),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            SizedBox(
              width: 80,
              child: Column(
                children: const [
                  Text(
                    '2.039',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
                  ),
                  Text(
                    'Seguidores',
                    style: TextStyle(fontSize: 15, fontWeight: FontWeight.w400),
                  ),
                ],
              ),
            ),
            SizedBox(
              width: 80,
              child: Column(
                children: const [
                  Text(
                    '3.478',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
                  ),
                  Text(
                    'Seguidos',
                    style: TextStyle(fontSize: 15, fontWeight: FontWeight.w400),
                  ),
                ],
              ),
            )
          ]),
        ),
      ),
      Padding(
        padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: const [
            Text('Ernesto Fatuarte',
                style: TextStyle(fontSize: 14, fontWeight: FontWeight.w500))
          ],
        ),
      ),
      Padding(
        padding: const EdgeInsets.only(bottom: 5),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SizedBox(
              width: 362,
              child: OutlinedButton(
                onPressed: () {
                  debugPrint('Editar Perfil');
                },
                child: const Text('Editar Perfil',
                    style: TextStyle(
                        fontSize: 15,
                        fontWeight: FontWeight.w500,
                        color: Colors.black)),
              ),
            )
          ],
        ),
      ),
      Padding(
        padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: const [
            Text('Historias destacadas',
                style: TextStyle(fontSize: 14, fontWeight: FontWeight.w700))
          ],
        ),
      ),
      Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: const [
            Text('Guarda tus historias favoritas en tu perfil',
                style: TextStyle(fontSize: 14, fontWeight: FontWeight.w400))
          ],
        ),
      ),
      Container(
          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
          width: MediaQuery.of(context).size.width,
          height: 125.0,
          child: ListView.separated(
            itemCount: 10,
            separatorBuilder: (BuildContext context, int index) {
              return const SizedBox(
                width: 20,
              );
            },
            itemBuilder: (_, i) => Column(
              children: const [
                SampleAvatar(),
                Padding(
                  padding: EdgeInsets.only(top: 3),
                  child: Text(
                    'Viajes',
                    style: TextStyle(fontSize: 14, fontWeight: FontWeight.w400),
                  ),
                )
              ],
            ),
            scrollDirection: Axis.horizontal,
          )),
      TabBar(
        indicatorColor: const Color(0xFF262626),
        controller: _controller,
        tabs: [
          Tab(
            icon: SvgPicture.asset(
              'assets/images/icon/grid.svg',
              color: selectedIndex == 0 ? Colors.black : Colors.grey,
              width: 18,
              semanticsLabel: 'My Posts',
            ),
          ),
          Tab(
            icon: SvgPicture.asset(
              'assets/images/icon/reels.svg',
              color: selectedIndex == 1 ? Colors.black : Colors.grey,
              width: 20,
              semanticsLabel: 'My Reels',
            ),
          ),
          Tab(
            icon: SvgPicture.asset(
              'assets/images/icon/play.svg',
              color: selectedIndex == 2 ? Colors.black : Colors.grey,
              width: 18,
              semanticsLabel: 'Lives',
            ),
          ),
          Tab(
            icon: SvgPicture.asset(
              'assets/images/icon/tag.svg',
              color: selectedIndex == 3 ? Colors.black : Colors.grey,
              width: 20,
              semanticsLabel: 'Tagged Posts',
            ),
          ),
        ],
      ),
      SizedBox(
        height: MediaQuery.of(context).size.width,
        child: TabBarView(
          controller: _controller,
          children: [
            ListView(
              children: [
                SizedBox(
                    height: MediaQuery.of(context).size.height,
                    width: MediaQuery.of(context).size.width,
                    child: const CardGridPicture()),
              ],
            ),
            ListView(
              children: [
                SizedBox(
                    height: MediaQuery.of(context).size.height,
                    width: MediaQuery.of(context).size.width,
                    child: const CardGridPicture()),
              ],
            ),
            ListView(
              children: [
                SizedBox(
                    height: MediaQuery.of(context).size.height,
                    width: MediaQuery.of(context).size.width,
                    child: const CardGridPicture()),
              ],
            ),
            ListView(
              children: [
                SizedBox(
                    height: MediaQuery.of(context).size.height,
                    width: MediaQuery.of(context).size.width,
                    child: const CardGridPicture()),
              ],
            ),
          ],
        ),
      ),
    ])));
  }
}
