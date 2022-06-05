import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_app/blocs/comment/comments_bloc.dart';
import 'package:flutter_app/models/comment_response.dart';
import 'package:flutter_app/repositories/comment/comment_repository.dart';
import 'package:flutter_app/repositories/comment/comment_repository_impl.dart';
import 'package:flutter_app/ui/create_comment_screen.dart';
import 'package:flutter_app/utils/shared_preferences.dart';
import 'package:flutter_app/widgets/rating_bar_widget.dart';
import 'package:flutter_app/widgets/shimmer_picture.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:like_button/like_button.dart';

class CommentList extends StatefulWidget {
  final id;
  const CommentList({Key? key, required this.id}) : super(key: key);

  @override
  _CommentListState createState() => _CommentListState();
}

class _CommentListState extends State<CommentList> {
  late CommentRepository commentRepository;
  late CommentsBloc _commentBloc;

  @override
  void initState() {
    PreferenceUtils.init();
    super.initState();
    commentRepository = CommentRepositoryImpl();
    _commentBloc = CommentsBloc(commentRepository)
      ..add(FetchSiteComments(widget.id));
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFFF5A5F),
          title: const Text('Comentarios'),
        ),
        body: BlocProvider(
            create: ((context) => _commentBloc),
            child: BlocBuilder<CommentsBloc, CommentsState>(
                builder: (context, state) {
              if (state is CommentsInitial) {
                return const Center(child: CircularProgressIndicator());
              } else if (state is CommentsFetched) {
                return _createBody(context);
              } else {
                return const Center(child: CircularProgressIndicator());
              }
            })),
        floatingActionButton: SizedBox(
          height: 70.0,
          width: 70.0,
          child: FittedBox(
            child: FloatingActionButton(
              elevation: 10,
              backgroundColor: const Color(0xFFFF5A5F),
              child: Image.asset(
                'assets/images/write_comment.png',
                width: 35,
                color: Colors.white,
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => CreateCommentScreen(
                      id: widget.id,
                    ),
                  ),
                );
              },
            ),
          ),
        ));
  }

  Widget _createBody(BuildContext context) {
    return BlocBuilder<CommentsBloc, CommentsState>(
      bloc: _commentBloc,
      builder: (context, state) {
        if (state is CommentsInitial) {
          return Column(
            children: const [
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
              Padding(
                padding: EdgeInsets.symmetric(vertical: 10),
                child: ShimmerListCard(),
              ),
            ],
          );
        } else if (state is CommentsFetched) {
          return _createCommentsView(context, state.comments);
        } else {
          return const Text('Not support');
        }
      },
    );
  }

  Widget _createCommentsView(
      BuildContext context, List<CommentResponse> comments) {
    return ListView.builder(
      itemBuilder: (context, index) {
        return _commentItem(context, comments[index]);
      },
      padding: const EdgeInsets.symmetric(vertical: 5),
      scrollDirection: Axis.vertical,
      itemCount: comments.length,
    );
  }

  Widget _commentItem(BuildContext context, CommentResponse comment) {
    String commentDescription = comment.description;
    String decodeCommentDescription =
        utf8.decode(latin1.encode(commentDescription), allowMalformed: true);
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
      height: MediaQuery.of(context).size.height * 0.213,
      child: Stack(
        children: [
          Positioned.fill(
            child: ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Image.network(comment.image, fit: BoxFit.cover),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
                height: 175,
                decoration: BoxDecoration(
                    borderRadius: const BorderRadius.all(Radius.circular(20)),
                    gradient: LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: [
                          Colors.black.withOpacity(0.8),
                          Colors.transparent
                        ]))),
          ),
          Positioned(
            top: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Row(
                children: [
                  Text(decodeCommentDescription,
                      style:
                          const TextStyle(color: Colors.white, fontSize: 20)),
                ],
              ),
            ),
          ),
          Positioned(
            top: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Row(
                children: [
                  Text(comment.cliente,
                      style:
                          const TextStyle(color: Colors.white, fontSize: 20)),
                ],
              ),
            ),
          ),
          Positioned(
            bottom: 0,
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: IconTheme(
                data: const IconThemeData(
                  color: Color(0xFFFF5A5F),
                  size: 25,
                ),
                child: RatingBarWidget(comment.rate),
              ),
            ),
          )
        ],
      ),
    );
  }
}
