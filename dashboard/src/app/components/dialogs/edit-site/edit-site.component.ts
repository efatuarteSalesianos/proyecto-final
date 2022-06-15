import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditSiteDTO } from 'src/app/models/dto/edit-site.dto';
import { SiteDTO } from 'src/app/models/dto/site.dto';
import { SiteResponse } from 'src/app/models/interfaces/site.interface';
import { PropietarioResponse } from 'src/app/models/interfaces/user.interface';
import { SiteService } from 'src/app/services/site.service';
import { UploadFileService } from 'src/app/services/upload-file.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-site',
  templateUrl: './edit-site.component.html',
  styleUrls: ['./edit-site.component.css']
})
export class EditSiteComponent implements OnInit {

  site!: SiteResponse;
  formBuilder = new FormBuilder();
  editSiteForm!: FormGroup;
  siteDto = new SiteDTO();
  propietariosList: PropietarioResponse [] = [];
  name = "";
  description = "";
  address = "";
  city = "";
  postalCode = "";
  email = "";
  phone = "";
  web = "";
  propietario = "";
  loading: boolean = false;
  fileName="";
  file: File | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) data: SiteResponse, private fileUploadService: UploadFileService, private siteService: SiteService, private snackBar: MatSnackBar, private userService: UserService) {
    this.site = data;
    this.name = data.name;
    //this.description = data.description;
    this.address = data.address;
    this.city = data.city;
    this.postalCode = data.postalCode;
    this.email = data.email;
    this.phone = data.phone;
    this.web = data.web;
    this.propietario = this.getPropietarioName(data.propietarioId)!;
    console.log(data);
  }

  ngOnInit(): void {
    this.editSiteForm = new FormGroup({
      name: new FormControl(this.name, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      description: new FormControl(this.description, [Validators.required, Validators.maxLength(100)]),
      address: new FormControl(this.address, [Validators.required]),
      city: new FormControl(this.city, [Validators.required]),
      postalCode: new FormControl(this.postalCode, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]),
      email: new FormControl(this.email, [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$')]),
      phone: new FormControl(this.phone, [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('^[0-9]*$')]),
      web: new FormControl(this.web, [Validators.required, Validators.pattern('^(http(s)?://)?(www.)?[a-z0-9]+([-.]{1}[a-z0-9]+).[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$')]),
      propietarioId: new FormControl(this.propietario, [Validators.required]),
    });

    this.editSiteForm = this.formBuilder.group({
      name: [this.name],
      description: [this.description],
      address: [this.address],
      city: [this.city],
      postalCode: [this.postalCode],
      email: [this.email],
      phone: [this.phone],
      web: [this.web],
      propietarioId: [this.propietario],
    });

    this.userService.listarPropietarios().subscribe(result => {
      this.propietariosList = result;
    });
  }

  editSite() {
    if(this.siteDto.name===""||this.siteDto.address===""||
      this.siteDto.city===""||this.siteDto.email===""||this.siteDto.postalCode===""
      ||this.siteDto.phone===""){
      this.snackBar.open('Faltan datos del negocio', 'Aceptar');
    }else{
      this.siteDto.name = this.editSiteForm.get('name')?.value;
      this.siteDto.address = this.editSiteForm.get('address')?.value;
      this.siteDto.city = this.editSiteForm.get('city')?.value;
      this.siteDto.email = this.editSiteForm.get('email')?.value;
      this.siteDto.postalCode = this.editSiteForm.get('postalCode')?.value;
      this.siteDto.phone = this.editSiteForm.get('phone')?.value;
      this.siteDto.web = this.editSiteForm.get('web')?.value;
      this.siteDto.propietarioId = this.editSiteForm.get('propietarioId')?.value;
      this.siteService.editSite(this.site.id, this.siteDto).subscribe(result => {
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

  getPropietarioName(id: string) {
      let propietario = this.propietariosList.find(x => x.id === id);
      return propietario?.fullName;
  }
}
