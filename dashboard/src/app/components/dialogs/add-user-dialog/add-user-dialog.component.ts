import { RegisterDTO } from './../../../models/dto/register.dto';
import { Component, OnInit } from '@angular/core';
import { UserResponse } from 'src/app/models/interfaces/user.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user-dialog',
  templateUrl: './add-user-dialog.component.html',
  styleUrls: ['./add-user-dialog.component.css']
})
export class AddUserDialogComponent implements OnInit {

  userDto = new RegisterDTO();
  user!: UserResponse;
  constructor(private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
  }

  addUser() {
    if(this.userDto.fullName===""||this.userDto.username===""||
      this.userDto.email===""||this.userDto.email===""||this.userDto.phone==="") {
      this.snackBar.open('Faltan datos del negocio', 'Aceptar');
    }else{
      this.userService.addUser(this.userDto).subscribe(result => {
        this.user = result;
        this.snackBar.open('Se ha creado el usuario correctamente', 'Aceptar');
        history.go(0)
      });
    }
  }

}
