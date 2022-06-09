import { CommentService } from './../../../services/comment.service';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { CommentResponse } from 'src/app/models/interfaces/comment.dto';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  displayedColumns: string[] = ['cliente', 'title', 'description', 'rate', 'image', 'actions'];
  comments!: CommentResponse[] | undefined;
  siteId!: number;
  dataSource = new MatTableDataSource(this.comments);

  constructor(private commentService: CommentService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.siteId = params['id'];
      this.commentService.getAllComments(this.siteId).subscribe(result => {
        this.comments = result;
        console.log(this.comments);
      });
    });
  }

  deleteComment(appointmentId: number) {
    this.commentService.deleteComment(this.siteId, appointmentId).subscribe(
      res => {
        console.log(res);
      }
    );
  }

  confirmDelete(commentId: number) {
    if (confirm('¿Estás seguro de eliminar este comentario?')) {
      this.deleteComment(commentId);
    } else {
      return;
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
