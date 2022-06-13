import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  baseApiUrl = "https://mysalonapi.s3.eu-west-3.amazonaws.com/"

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<any> {

      const formData = new FormData();

      formData.append("file", file, file.name);

      return this.http.post(this.baseApiUrl, formData)
  }
}
