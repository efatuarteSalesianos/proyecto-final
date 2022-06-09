import { AppointmentDTO } from './../models/dto/appointment.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { AppointmentResponse } from '../models/interfaces/appointment.dto';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })
};

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  addAppointment(siteId: number, appointment: AppointmentDTO) {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/appointment`;
    return this.http.post(requestUrl, appointment, DEFAULT_HEADERS);
  }

  getAllAppointments(id: number): Observable<AppointmentResponse[]> {
    let requestUrl = `${environment.API_BASE_URL}/site/${id}/appointment`;
    return this.http.get<AppointmentResponse[]>(requestUrl, DEFAULT_HEADERS);
  }

  getAppointmentById(siteId: number, appointmentId: number): Observable<AppointmentResponse> {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/appointment/${appointmentId}`;
    return this.http.get<AppointmentResponse>(requestUrl, DEFAULT_HEADERS);
  }

  editAppointment(siteid: number, appointmentId: number, appointment: AppointmentDTO): Observable<AppointmentResponse> {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteid}/appointment/${appointmentId}`;
    return this.http.put<AppointmentResponse>(requestUrl, appointment, DEFAULT_HEADERS);
  }

  deleteAppointment(siteId: number, appointmentId: number) {
    let requestUrl = `${environment.API_BASE_URL}/site/${siteId}/appointment/${appointmentId}`;
    return this.http.delete(requestUrl, DEFAULT_HEADERS);
  }
}
