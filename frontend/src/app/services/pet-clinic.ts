import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PetClinic {

  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  getOwners(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/owners`);
  }

  getPets(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/pets`);
  }

  getVets(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/vets`);
  }

  getAppointments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments`);
  }

  getAppointmentDetails(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments/details`);
  }

  getVetsAboveAverage(): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.baseUrl}/vets/above-average-appointments`
    );
  }

  getConsultationFee(vetId: number): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/vets/${vetId}/fee`
    );
  }

  bookAppointment(appointment: any): Observable<string> {
    return this.http.post<string>(
      `${this.baseUrl}/appointments/book`,
      appointment,
      {
        responseType: 'text' as 'json'
      }
    );
  }
}