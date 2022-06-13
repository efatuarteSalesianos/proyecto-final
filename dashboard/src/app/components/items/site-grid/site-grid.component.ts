import { SiteService } from 'src/app/services/site.service';
import { Component, Input, OnInit } from '@angular/core';
import { SiteResponse } from 'src/app/models/interfaces/site.interface';
import { AddSiteDialogComponent } from '../../dialogs/add-site-dialog/add-site-dialog.component';
import { EditSiteComponent } from '../../dialogs/edit-site/edit-site.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-site-grid',
  templateUrl: './site-grid.component.html',
  styleUrls: ['./site-grid.component.css']
})
export class SiteGridComponent implements OnInit {

  @Input() site! : SiteResponse;

  constructor(private siteService: SiteService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
  }

  deleteSite(id: number) {
    this.siteService.deleteSite(id).subscribe(res => {
      console.log(res);
      this.ngOnInit();
    });
  }

  openDeleteDialog(id: number) {
    if(confirm("¿Estás seguro de que quieres borrar este negocio?")) {
      this.deleteSite(id);
    } else {
      return;
    }
  }

  openDialogNewSite(){
    this.dialog.open(AddSiteDialogComponent, {
      height: '450px',
      width: '400px',
    });
  }

  openDialogEditSite(site: SiteResponse){
    this.dialog.open(EditSiteComponent, {
      height: '450px',
      width: '400px',
    });
  }

  openDetailSite(id: number) {
    this.router.navigate(['/site', id]);
  }

  //build rate bar with stars depends on site rate
  buildRateBar(rate: number) {
    let rateBar = '';
    for(let i = 0; i < rate; i++) {
      rateBar += '<i class="fas fa-star"></i>';
    }
    return rateBar;
  }
}
