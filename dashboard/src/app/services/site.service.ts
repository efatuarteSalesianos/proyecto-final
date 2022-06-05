import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SiteResponse } from '../models/interfaces/site.interface';
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
export class SiteService {

  constructor(private http: HttpClient) { }

  getAllSites(): Observable<SiteResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/site/`;
    return this.http.get<SiteResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  deleteSite(id: number) {
    let requestUrl = `${environment.API_BASE_URL}/site/${id}`;
    return this.http.delete(requestUrl, DEFAULT_HEADERS);
  }
}
