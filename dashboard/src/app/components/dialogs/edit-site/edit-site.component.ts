import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditSiteDTO } from 'src/app/models/dto/edit-site.dto';
import { SiteResponse } from 'src/app/models/interfaces/site.interface';
import { PropietarioResponse } from 'src/app/models/interfaces/user.interface';
import { SiteService } from 'src/app/services/site.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-site',
  templateUrl: './edit-site.component.html',
  styleUrls: ['./edit-site.component.css']
})
export class EditSiteComponent implements OnInit {

  siteDto = new EditSiteDTO();
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
