import { LoginDTO } from './../models/dto/login.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../models/dto/register.dto';
import { RegisterResponse } from '../models/interfaces/register.interface';
import { LoginResponse } from '../models/interfaces/login.interface';
import { environment } from 'src/environments/environment.prod';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(loginDto: LoginDTO): Observable<LoginResponse> {
    let requestUrl: string = `${environment.API_BASE_URL}/auth/login`;
    return this.http.post<LoginResponse>(requestUrl, loginDto, DEFAULT_HEADERS);
  }

  registerUser(registerDto: RegisterDTO): Observable<RegisterResponse> {
    let requestUrl: string = `${environment.API_BASE_URL}/auth/register`;
    return this.http.post<RegisterResponse>(requestUrl, registerDto, DEFAULT_HEADERS);
  }

  registerPropietario(registerDto: RegisterDTO): Observable<RegisterResponse> {
    let requestUrl: string = `${environment.API_BASE_URL}/auth/register/propietario`;
    return this.http.post<RegisterResponse>(requestUrl, registerDto, DEFAULT_HEADERS);
  }

  registerAdmin(registerDto: RegisterDTO): Observable<RegisterResponse> {
    let requestUrl: string = `${environment.API_BASE_URL}/auth/register/admin`;
    return this.http.post<RegisterResponse>(requestUrl, registerDto, DEFAULT_HEADERS);
  }
}
