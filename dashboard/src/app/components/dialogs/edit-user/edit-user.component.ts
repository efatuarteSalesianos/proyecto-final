import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { EditUserDTO } from 'src/app/models/dto/edit-user.dto';
import { UserResponse } from 'src/app/models/interfaces/user.interface';
import { UploadFileService } from 'src/app/services/upload-file.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  editUserForm!: FormGroup;
  formBuilder = new FormBuilder();
  userDto = new EditUserDTO();
  user!: UserResponse;
  fullName = "";
  username = "";
  email = "";
  birthDate = new Date();
  phone = "";
  loading: boolean = false; // Flag variable
  fileName="";
  file: File | null = null;

  constructor(@Inject(MAT_DIALOG_DATA) data: UserResponse, private fileUploadService: UploadFileService, private snackBar: MatSnackBar, private userService: UserService, private route : ActivatedRoute) {
    this.user = data;
    this.fullName = data.fullName;
    this.username = data.username;
    this.email = data.email;
    this.birthDate = data.birthDate;
    this.phone = data.phone;
    this.fileName = data.avatar;
  }

  ngOnInit(): void {
    this.editUserForm = new FormGroup({
      fullName: new FormControl(this.fullName, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      username: new FormControl(this.username, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      email: new FormControl(this.email, [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      birthDate: new FormControl(this.birthDate, [Validators.required, Validators.pattern('^[0-9]{4}-[0-9]{2}-[0-9]{2}$')]),
      phone: new FormControl(this.phone, [Validators.required, Validators.pattern('^[0-9]{9}$'), Validators.minLength(9), Validators.maxLength(9)]),
      avatar: new FormControl(this.fileName, [Validators.required]),
    });

    this.editUserForm = this.formBuilder.group({
      fullName: [this.fullName],
      username: [this.username],
      email: [this.email],
      birthDate: [this.birthDate],
      phone: [this.phone],
      avatar: [this.fileName],
    });
  }

  editUser() {
    if(this.userDto.fullName===""||this.userDto.username===""||
      this.userDto.email===""||this.userDto.email===""||this.userDto.phone==="") {
      this.snackBar.open('Faltan datos del usuario', 'Aceptar');
    }else{
      this.userDto.fullName = this.editUserForm.get('fullName')?.value;
      this.userDto.username = this.editUserForm.get('username')?.value;
      this.userDto.email = this.editUserForm.get('email')?.value;
      this.userDto.birthDate = this.editUserForm.get('birthDate')?.value;
      this.userDto.phone = this.editUserForm.get('phone')?.value;
      this.userDto.avatar = this.editUserForm.get('avatar')?.value;
      this.userService.editUser(this.userDto, this.user.id).subscribe(result => {
        this.user = result;
        this.snackBar.open('Se ha guardado el usuario correctamente', 'Aceptar');
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

  //Parse date to string with format dd/mm/yyyy
  parseDate(date: Date): string {
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    return day + "/" + month + "/" + year;
  }
}
