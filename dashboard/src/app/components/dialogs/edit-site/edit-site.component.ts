import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
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

  editSiteForm!: FormGroup;
  siteDto = new SiteDTO();
  site!: SiteResponse;
  propietariosList: PropietarioResponse [] = [];
  loading: boolean = false; // Flag variable
  fileName="";
  file: File | null = null;
  constructor(private fileUploadService: UploadFileService, private siteService: SiteService, private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
    this.editSiteForm = new FormGroup({
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
