import 'package:flutter/material.dart';

void main() {
  runApp(const MyFavouritesScreen());
}

class MyFavouritesScreen extends StatelessWidget {
  const MyFavouritesScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        // Remove the debug banner
        debugShowCheckedModeBanner: false,
        title: 'Mis Favoritos',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const FavouritesScreen());
  }
}

class FavouritesScreen extends StatefulWidget {
  const FavouritesScreen({Key? key}) : super(key: key);

  @override
  _FavouritesScreenState createState() => _FavouritesScreenState();
}

class _FavouritesScreenState extends State<FavouritesScreen> {
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
            title: const Text('Mis Favoritos'),
            actions: [
              IconButton(
                icon: const Icon(Icons.exit_to_app),
                onPressed: () {},
              ),
            ],
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