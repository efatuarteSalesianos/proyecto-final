import { SiteResponse } from './../../../models/interfaces/site.interface';
import { UserService } from './../../../services/user.service';
import { PropietarioResponse } from './../../../models/interfaces/user.interface';
import { SiteDTO } from './../../../models/dto/site.dto';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SiteService } from 'src/app/services/site.service';

@Component({
  selector: 'app-add-site-dialog',
  templateUrl: './add-site-dialog.component.html',
  styleUrls: ['./add-site-dialog.component.css']
})
export class AddSiteDialogComponent implements OnInit {

  siteDto = new SiteDTO();
  site!: SiteResponse;
  propietariosList: PropietarioResponse [] = [];
  constructor(private siteService: SiteService, private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.listarPropietarios().subscribe(result => {
      this.propietariosList = result;
    });
  }

  addSite() {
    if(this.siteDto.name===""||this.siteDto.address===""||
      this.siteDto.city===""||this.siteDto.email===""||this.siteDto.postalCode===""
      ||this.siteDto.phone===""){
      this.snackBar.open('Faltan datos del negocio', 'Aceptar');
    }else{
      this.siteService.addSite(this.siteDto).subscribe(result => {
        this.site = result;
        this.snackBar.open('Se ha creado el negocio correctamente', 'Aceptar');
        history.go(0)
      });
    }
  }

}
