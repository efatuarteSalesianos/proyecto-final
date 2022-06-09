import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { CommentDTO } from '../models/dto/comment.dto';
import { CommentResponse } from '../models/interfaces/comment.dto';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })
};

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  addComment(siteId: number, comment: CommentDTO) {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/comment`;
    return this.http.post(requestUrl, comment, DEFAULT_HEADERS);
  }

  getAllComments(id: number): Observable<CommentResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/site/${id}/comment`;
    return this.http.get<CommentResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  getCommentById(siteId: number, commentId: number): Observable<CommentResponse> {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/comment/${commentId}`;
    return this.http.get<CommentResponse>(requestUrl, DEFAULT_HEADERS);
  }

  editComment(siteid: number, commentId: number, comment: CommentDTO): Observable<CommentResponse> {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteid}/comment/${commentId}`;
    return this.http.put<CommentResponse>(requestUrl, comment, DEFAULT_HEADERS);
  }

  deleteComment(siteId: number, commentId: number) {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/comment/${commentId}`;
    return this.http.delete(requestUrl, DEFAULT_HEADERS);
  }
}
