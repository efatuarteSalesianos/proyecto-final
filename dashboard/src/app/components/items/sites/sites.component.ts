import { EditSiteComponent } from './../../dialogs/edit-site/edit-site.component';
import { Router } from '@angular/router';
import { AddSiteDialogComponent } from './../../dialogs/add-site-dialog/add-site-dialog.component';
import { SiteResponse } from '../../../models/interfaces/site.interface';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { SiteService } from 'src/app/services/site.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-sites',
  templateUrl: './sites.component.html',
  styleUrls: ['./sites.component.css']
})
export class SitesComponent implements OnInit {

  displayedColumns: string[] = ['scaledFileUrl', 'name', 'address', 'city', 'postalCode', 'phone', 'totalComments', 'rate', 'actions'];

  negocios!: SiteResponse[];
  dataSource = new MatTableDataSource(this.negocios);

  constructor(private siteService: SiteService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.siteService.getAllSites().subscribe(res => {
      this.negocios = res;
      this.dataSource = new MatTableDataSource(this.negocios);
    });
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

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewSite(){
    this.dialog.open(AddSiteDialogComponent, {
      height: '450px',
      width: '400px',
    });
  }

  openDialogEditSite(site: SiteResponse) {
    this.dialog.open(EditSiteComponent, {
      height: '450px',
      width: '400px',
      data: site
    });
  }

  openDetailSite(id: number) {
    this.router.navigate(['/site', id]);
  }

}
