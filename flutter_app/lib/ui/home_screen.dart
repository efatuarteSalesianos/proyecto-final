import 'package:flutter/material.dart';
import 'package:flutter_app/ui/login_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';

void main() {
  runApp(const MyHomeScreen());
}

class MyHomeScreen extends StatelessWidget {
  const MyHomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        // Remove the debug banner
        debugShowCheckedModeBanner: false,
        title: 'MySalon',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const HomeScreen());
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            floating: true,
            pinned: true,
            snap: false,
            centerTitle: false,
            title: const Text('MySalon'),
            shadowColor: const Color(0xFFFF5A5F),
            backgroundColor: const Color(0xFFFF5A5F),
            foregroundColor: const Color(0xFFFFFFFF),
            actions: [
              IconButton(
                  icon: const Icon(Icons.exit_to_app),
                  onPressed: () => {
                        PreferenceUtils.clear(),
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const LoginScreen()))
                      }),
            ],
            bottom: AppBar(
              backgroundColor: const Color(0xFFFF5A5F),
              title: Container(
                width: double.infinity,
                height: 40,
                color: Colors.white,
                child: const Center(
                  child: TextField(
                    decoration: InputDecoration(
                        hintText: '¿Dónde quieres buscar?',
                        prefixIcon: Icon(Icons.search),
                        suffixIcon: Icon(Icons.map_outlined)),
                  ),
                ),
              ),
            ),
          ),
          SliverList(
            delegate: SliverChildBuilderDelegate(
              (BuildContext context, int index) {
                return Card(
                  margin: const EdgeInsets.all(10),
                  child: Container(
                    color: Colors.blue[100 * (index % 9 + 1)],
                    height: 100,
                    alignment: Alignment.center,
                    child: Text(
                      "Item $index",
                      style: const TextStyle(fontSize: 30),
                    ),
                  ),
                );
              },
              childCount: 1000, // 1000 list items
            ),
          ),
        ],
      ),
    );
  }
}
