import 'package:flutter/material.dart';

void main() {
  runApp(const MyBookingsScreen());
}

class MyBookingsScreen extends StatelessWidget {
  const MyBookingsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Mis Reservas',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const BookingsScreen());
  }
}

class BookingsScreen extends StatefulWidget {
  const BookingsScreen({Key? key}) : super(key: key);

  @override
  _BookingsScreenState createState() => _BookingsScreenState();
}

class _BookingsScreenState extends State<BookingsScreen> {
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
            title: const Text('Mis Reservas'),
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
