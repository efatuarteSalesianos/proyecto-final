import { environment } from './../../environments/environment.prod';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostResponse } from '../models/interfaces/post.interface';
import { Observable } from 'rxjs';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })
};

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  listarPosts(): Observable<PostResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/post/public`;
    return this.http.get<PostResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  deletePost(id: number) {
    let requestUrl = `${environment.API_BASE_URL}/post/${id}`;
    return this.http.delete(requestUrl, DEFAULT_HEADERS);
  }
}
