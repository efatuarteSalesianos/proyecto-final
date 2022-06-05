import 'package:flutter/material.dart';
import 'package:flutter_app/ui/bookings_screen.dart';
import 'package:flutter_app/ui/login_screen.dart';

class BookingDoneScreen extends StatefulWidget {
  final name;
  final image;
  const BookingDoneScreen({Key? key, required this.name, required this.image})
      : super(key: key);

  @override
  _BookingDoneScreenState createState() => _BookingDoneScreenState();
}

class _BookingDoneScreenState extends State<BookingDoneScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        shadowColor: const Color(0xFFFF5A5F),
        backgroundColor: const Color(0xFFFF5A5F),
        foregroundColor: const Color(0xFFFFFFFF),
        title: const Text('Cita Confirmada'),
        actions: [
          IconButton(
              icon: const Icon(Icons.exit_to_app),
              onPressed: () => {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => const LoginScreen()))
                  }),
        ],
      ),
      body: BookingDone(name: widget.name, image: widget.image),
    );
  }
}

class BookingDone extends StatefulWidget {
  final name;
  final image;
  const BookingDone({Key? key, required this.name, required this.image})
      : super(key: key);

  @override
  _BookingDoneState createState() => _BookingDoneState();
}

class _BookingDoneState extends State<BookingDone> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 0, horizontal: 0),
      child: Column(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Column(children: [
            Padding(
              padding: EdgeInsets.symmetric(vertical: 40, horizontal: 20),
              child: Text(
                  "¡Felicidades! La reserva en ${widget.name} ha sido un éxito.",
                  style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
            ),
            Image.network(widget.image),
          ]),
          Padding(
            padding:
                const EdgeInsets.symmetric(vertical: 50.0, horizontal: 20.0),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                primary: Colors.white,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
              ),
              onPressed: () => {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const BookingsScreen()))
              },
              child: Container(
                alignment: Alignment.center,
                height: 60.0,
                child: const Text(
                  "Mis Reservas",
                  style: TextStyle(
                      color: Color(0xFFFF5A5F),
                      fontWeight: FontWeight.bold,
                      fontSize: 30.0),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
