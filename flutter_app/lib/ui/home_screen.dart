import 'package:flutter/material.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _MyHomeScreenState createState() => _MyHomeScreenState();
}

class _MyHomeScreenState extends State<HomeScreen> {
  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Column(
          children: [
            Row(
              children: [
                Column(
                  children: [
                    Container(
                        decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(color: Colors.white, width: 3)),
                        child: ClipRRect(
                            borderRadius: BorderRadius.circular(50),
                            child: Image.network(
                                "https://phantom-elmundo.unidadeditorial.es/37812441ebf2e1d7b564b23077108513/resize/640/assets/multimedia/imagenes/2021/11/17/16371506566138.png"))),
                  ],
                ),
                Column(
                  children: [
                    Container(
                        decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(color: Colors.white, width: 3)),
                        child: ClipRRect(
                            borderRadius: BorderRadius.circular(50),
                            child: Image.network(
                                "https://static2-sevilla.abc.es/media/sevilla/2016/11/09/s/FOTO-PORTADA-TOP-BARBERIAS-k28D--620x349@abc.jpg"))),
                  ],
                ),
              ],
            ),
            Row(
              children: [
                Column(
                  children: [
                    Container(
                        decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(color: Colors.white, width: 3)),
                        child: ClipRRect(
                            borderRadius: BorderRadius.circular(50),
                            child: Image.network(
                                "https://www.materialestetica.com/blog/wp-content/uploads/2019/03/3-pasos-para-montar-un-centro-de-estetica-que-debo-saber.jpg"))),
                  ],
                ),
                Column(
                  children: [
                    Container(
                        decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(color: Colors.white, width: 3)),
                        child: ClipRRect(
                            borderRadius: BorderRadius.circular(50),
                            child: Image.network(
                                "https://fra1.digitaloceanspaces.com/places/uploads/place/image/file/253757/06057E00-B0E9-4A66-BCB2-6D00781C143A.jpeg"))),
                  ],
                ),
              ],
            ),
          ],
        ),
        Row()
      ],
    );
  }
}
