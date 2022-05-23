import { PostResponse } from './../../../models/interfaces/post.interface';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  displayedColumns: string[] = ['id', 'scaledFileUrl', 'title', 'description', 'username', 'avatar', 'actions'];

  publicaciones!: PostResponse[];
  dataSource = new MatTableDataSource(this.publicaciones);

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.listarPosts().subscribe(res => {
      this.publicaciones = res;
      this.dataSource = new MatTableDataSource(this.publicaciones);
    });
  }

  deletePost(id: number) {
    this.postService.deletePost(id).subscribe(res => {
      console.log(res);
    });
  }

  openDeleteDialog(id: number) {
    if(confirm("¿Estás seguro de que quieres borrar este post?")) {
      this.deletePost(id);
      this.ngOnInit();
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
