import { UploadFileService } from './../../../services/upload-file.service';
import { SiteResponse } from './../../../models/interfaces/site.interface';
import { UserService } from './../../../services/user.service';
import { PropietarioResponse } from './../../../models/interfaces/user.interface';
import { SiteDTO } from './../../../models/dto/site.dto';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SiteService } from 'src/app/services/site.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-site-dialog',
  templateUrl: './add-site-dialog.component.html',
  styleUrls: ['./add-site-dialog.component.css']
})
export class AddSiteDialogComponent implements OnInit {

  newSiteForm!: FormGroup;
  siteDto = new SiteDTO();
  site!: SiteResponse;
  propietariosList: PropietarioResponse [] = [];
  loading: boolean = false; // Flag variable
  fileName="";
  file: File | null = null;
  constructor(private fileUploadService: UploadFileService, private siteService: SiteService, private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
    this.newSiteForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      postalCode: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required]),
      web: new FormControl('', [Validators.required]),
      propietarioId: new FormControl('', [Validators.required]),
    });
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
      this.siteDto.name = this.newSiteForm.get('name')?.value;
      this.siteDto.address = this.newSiteForm.get('address')?.value;
      this.siteDto.city = this.newSiteForm.get('city')?.value;
      this.siteDto.email = this.newSiteForm.get('email')?.value;
      this.siteDto.postalCode = this.newSiteForm.get('postalCode')?.value;
      this.siteDto.phone = this.newSiteForm.get('phone')?.value;
      this.siteDto.web = this.newSiteForm.get('web')?.value;
      this.siteDto.propietarioId = this.newSiteForm.get('propietarioId')?.value;
      this.siteService.addSite(this.siteDto).subscribe(result => {
        this.site = result;
        this.snackBar.open('Se ha creado el negocio correctamente', 'Aceptar');
        history.go(0)
      });
    }
  }

  handleFileInput(files: FileList) {
    this.file = files.item(0);
    this.fileName = this.file!.name;
    this.fileUploadService.upload(this.file!).subscribe(result => {
      console.log(result);
    });
  }

}
