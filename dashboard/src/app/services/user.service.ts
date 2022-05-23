import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserResponse } from '../models/interfaces/user.interface';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  listarUsuarios(): Observable<UserResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/users`;
    return this.http.get<UserResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  giveAdmin(username: string): Observable<UserResponse> {
    let requestUrl = `${environment.API_BASE_URL}/users/${username}/admin`;
    return this.http.put<UserResponse>(requestUrl, null, DEFAULT_HEADERS);
  }
}
