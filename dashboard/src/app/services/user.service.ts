import { RegisterDTO } from './../models/dto/register.dto';
import { SiteResponse } from './../models/interfaces/site.interface';
import { SiteDTO } from './../models/dto/site.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { PropietarioResponse, UserResponse } from '../models/interfaces/user.interface';

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

  addUser(userDto: RegisterDTO): Observable<UserResponse> {
    let requestUrl = `${environment.API_BASE_URL}/auth/register`;
    return this.http.post<UserResponse>(requestUrl, userDto, DEFAULT_HEADERS);
  }

  listarUsuarios(): Observable<UserResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/users`;
    return this.http.get<UserResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  listarPropietarios(): Observable<PropietarioResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/users/propietarios`;
    return this.http.get<PropietarioResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  getUserByUsername(username: string): Observable<UserResponse> {
    let requestUrl = `${environment.API_BASE_URL}/profile/${username}`;
    return this.http.get<UserResponse>(requestUrl, DEFAULT_HEADERS);
  }

  getPropietarioByUsername(username: string): Observable<UserResponse> {
    let requestUrl = `${environment.API_BASE_URL}/profile/propietario/${username}`;
    return this.http.get<UserResponse>(requestUrl, DEFAULT_HEADERS);
  }

  deleteUser(id: number) {
    let requestUrl = `${environment.API_BASE_URL}/user/${id}`;
    return this.http.delete(requestUrl, DEFAULT_HEADERS);
  }

  giveAdmin(username: string): Observable<UserResponse> {
    let requestUrl = `${environment.API_BASE_URL}/users/${username}/admin`;
    return this.http.put<UserResponse>(requestUrl, null, DEFAULT_HEADERS);
  }
}
