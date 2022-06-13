import { CommentResponse } from './../../../models/interfaces/comment.dto';
import { SiteResponse } from './../../../models/interfaces/site.interface';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentResponse } from 'src/app/models/interfaces/appointment.dto';
import { AppointmentService } from 'src/app/services/appointment.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {

  displayedColumns: string[] = ['cliente', 'date', 'hour', 'description', 'actions'];
  appointments!: AppointmentResponse[] | undefined;
  siteId!: number;
  dataSource = new MatTableDataSource(this.appointments);


  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.siteId = params['id'];
      this.appointmentService.getAllAppointments(this.siteId).subscribe(result => {
        this.appointments = result;
        this.dataSource = new MatTableDataSource(this.appointments);
        console.log(this.appointments);
      });
    });
  }

  deleteAppointment(appointmentId: number) {
    this.appointmentService.deleteAppointment(this.siteId, appointmentId).subscribe(
      res => {
        console.log(res);
      }
    );
  }

  confirmDelete(appointmentId: number) {
    if (confirm('¿Estás seguro de eliminar esta cita?')) {
      this.deleteAppointment(appointmentId);
    } else {
      return;
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  formatDate(date: string) {
    const dateArray = date.split('-');
    const day = dateArray[2];
    const month = dateArray[1];
    const months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    return `${day} de ${months[Number(month) - 1]}`;
  }

}
