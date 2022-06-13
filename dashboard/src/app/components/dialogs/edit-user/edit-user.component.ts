import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditUserDTO } from 'src/app/models/dto/editUser.dto';
import { RegisterDTO } from 'src/app/models/dto/register.dto';
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
  userDto = new RegisterDTO();
  user!: UserResponse;
  loading: boolean = false; // Flag variable
  fileName="";
  file: File | null = null;

  constructor(private fileUploadService: UploadFileService, private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
    this.editUserForm = new FormGroup({
      fullName: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      birthDate: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
    })
  }

  addUser() {
    if(this.userDto.fullName===""||this.userDto.username===""||
      this.userDto.email===""||this.userDto.email===""||this.userDto.phone==="") {
      this.snackBar.open('Faltan datos del usuario', 'Aceptar');
    }else{
      this.userDto.fullName = this.editUserForm.get('fullName')?.value;
      this.userDto.username = this.editUserForm.get('username')?.value;
      this.userDto.email = this.editUserForm.get('email')?.value;
      this.userDto.birthDate = this.editUserForm.get('birthDate')?.value;
      this.userDto.phone = this.editUserForm.get('phone')?.value;
      this.userDto.password = this.editUserForm.get('password')?.value;
      this.userService.addUser(this.userDto).subscribe(result => {
        this.user = result;
        this.snackBar.open('Se ha creado el usuario correctamente', 'Aceptar');
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
