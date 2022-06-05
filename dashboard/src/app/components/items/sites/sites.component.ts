import { SiteResponse } from '../../../models/interfaces/site.interface';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { SiteService } from 'src/app/services/site.service';

@Component({
  selector: 'app-sites',
  templateUrl: './sites.component.html',
  styleUrls: ['./sites.component.css']
})
export class SitesComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'address', 'city', 'postalCode', 'phone', 'totalComments', 'rate', 'scaledFileUrl', 'actions'];

  negocios!: SiteResponse[];
  dataSource = new MatTableDataSource(this.negocios);

  constructor(private siteService: SiteService) { }

  ngOnInit(): void {
    this.siteService.getAllSites().subscribe(res => {
      this.negocios = res;
      this.dataSource = new MatTableDataSource(this.negocios);
    });
  }

  deleteSite(id: number) {
    this.siteService.deleteSite(id).subscribe(res => {
      console.log(res);
    });
  }

  openDeleteDialog(id: number) {
    if(confirm("¿Estás seguro de que quieres borrar este negocio?")) {
      this.deleteSite(id);
      this.ngOnInit();
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
