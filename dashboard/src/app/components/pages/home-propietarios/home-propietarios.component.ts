import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { SiteResponse } from 'src/app/models/interfaces/site.interface';
import { SiteService } from 'src/app/services/site.service';
import { AddSiteDialogComponent } from '../../dialogs/add-site-dialog/add-site-dialog.component';
import { EditSiteComponent } from '../../dialogs/edit-site/edit-site.component';

@Component({
  selector: 'app-home-propietarios',
  templateUrl: './home-propietarios.component.html',
  styleUrls: ['./home-propietarios.component.css']
})
export class HomePropietariosComponent implements OnInit {

  displayedColumns: string[] = ['scaledFileUrl', 'name', 'address', 'city', 'postalCode', 'phone', 'totalComments', 'rate', 'actions'];

  negocios!: SiteResponse[];
  dataSource = new MatTableDataSource(this.negocios);

  constructor(private siteService: SiteService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.siteService.getAllSitesByPropietario(localStorage.getItem('username')!).subscribe(res => {
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

  openDialogEditSite(site: SiteResponse){
    this.dialog.open(EditSiteComponent, {
      height: '450px',
      width: '400px',
    });
  }

  openDetailSite(id: number) {
    this.router.navigate(['/site', id]);
  }

}
