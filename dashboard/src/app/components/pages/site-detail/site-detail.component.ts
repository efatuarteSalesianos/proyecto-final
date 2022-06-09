import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentResponse } from 'src/app/models/interfaces/appointment.dto';
import { CommentResponse } from 'src/app/models/interfaces/comment.dto';
import { SiteResponse } from 'src/app/models/interfaces/site.interface';
import { SiteService } from 'src/app/services/site.service';

@Component({
  selector: 'app-site-detail',
  templateUrl: './site-detail.component.html',
  styleUrls: ['./site-detail.component.css']
})
export class SiteDetailComponent implements OnInit {

  site!: SiteResponse;
  commentsSite: CommentResponse[] | undefined;
  appointmentsSite: AppointmentResponse[] | undefined;
  siteId!: number;

  constructor(private siteService: SiteService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.siteId = params['id'];
      this.siteService.getSiteById(this.siteId).subscribe(result => {
        this.site = result;
        console.log(this.site);
      });
    });
  }

}
