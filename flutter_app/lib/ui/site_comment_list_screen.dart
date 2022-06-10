import 'dart:convert';

import 'package:expandable/expandable.dart';
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
    return Container(
      decoration: const BoxDecoration(
        color: Color(0xFFE5E5E5),
      ),
      child: ListView.builder(
        itemBuilder: (context, index) {
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            child: _commentItem(context, comments[index]),
          );
        },
        padding: const EdgeInsets.symmetric(vertical: 5),
        scrollDirection: Axis.vertical,
        itemCount: comments.length,
      ),
    );
  }

  Widget _commentItem(BuildContext context, CommentResponse comment) {
    String name = comment.cliente;
    String decodedClienteName =
        utf8.decode(latin1.encode(name), allowMalformed: true);
    String commentTitle = comment.title;
    String decodeCommentTitle =
        utf8.decode(latin1.encode(commentTitle), allowMalformed: true);
    String commentDescription = comment.description;
    String decodeCommentDescription =
        utf8.decode(latin1.encode(commentDescription), allowMalformed: true);
    return SizedBox(
      //TODO: Ajustar altura según texto
      height: MediaQuery.of(context).size.height * 0.29,
      child: Card(
        color: Colors.white,
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 10.0),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(top: 6.0),
                        child: Text(decodedClienteName,
                            style: const TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.w600,
                            )),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 30),
                        child: Row(
                          children: [
                            Text(comment.rate.toString(),
                                style: const TextStyle(
                                  fontSize: 20,
                                  color: Color(0xFFFF5A5F),
                                  fontWeight: FontWeight.bold,
                                )),
                            const Padding(
                              padding: EdgeInsets.only(left: 3.0),
                              child: IconTheme(
                                  data: IconThemeData(
                                      color: Color(0xFFFF5A5F), size: 28),
                                  child: Icon(Icons.star)),
                            )
                          ],
                        ),
                      ),
                    ]),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 15.0),
                child: SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: ExpandablePanel(
                      theme: const ExpandableThemeData(
                        headerAlignment: ExpandablePanelHeaderAlignment.center,
                        tapBodyToCollapse: true,
                      ),
                      header: Text(
                        decodeCommentTitle,
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w700,
                        ),
                        maxLines: 2,
                        overflow: TextOverflow.ellipsis,
                      ),
                      collapsed: Padding(
                        padding: const EdgeInsets.only(top: 10.0),
                        child: Text(
                          decodeCommentDescription,
                          style: const TextStyle(
                            fontSize: 15,
                          ),
                          softWrap: true,
                          maxLines: 3,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                      expanded: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Padding(
                            padding: const EdgeInsets.only(top: 10.0),
                            child: Text(
                              decodeCommentDescription,
                              style: const TextStyle(
                                fontSize: 15,
                              ),
                              softWrap: true,
                              overflow: TextOverflow.fade,
                            ),
                          ),
                        ],
                      ),
                      builder: (_, collapsed, expanded) {
                        return Expandable(
                          collapsed: collapsed,
                          expanded: expanded,
                          theme: const ExpandableThemeData(crossFadePoint: 0),
                        );
                      },
                    )),
              )
            ],
          ),
        ),
      ),
    );
  }
}
