import { SiteResponse } from './../../../models/interfaces/site.interface';
import { SiteDTO } from './../../../models/dto/site.dto';
import { SiteService } from './../../../services/site.service';
import { UserService } from './../../../services/user.service';
import { UserResponse } from './../../../models/interfaces/user.interface';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-site-dialog',
  templateUrl: './add-site-dialog.component.html',
  styleUrls: ['./add-site-dialog.component.css']
})
export class AddSiteDialogComponent implements OnInit {

  createSite!: SiteDTO;
  site!: SiteResponse;
  propietariosList: UserResponse[] = [];
  propietarioId!: number;
  constructor( private userService: UserService, private route: ActivatedRoute, private siteService: SiteService) { }

  ngOnInit(): void {
    this.userService.listarPropietarios().subscribe(propietario =>{
      this.propietariosList = propietario;
    });
  }

    addSite(){
      this.siteService.addSite(this.createSite).subscribe(result => {
        this.site = result;
      });
    }

}
