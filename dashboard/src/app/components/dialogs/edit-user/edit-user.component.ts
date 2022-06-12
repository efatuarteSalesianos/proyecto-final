import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditUserDTO } from 'src/app/models/dto/editUser.dto';
import { UserResponse } from 'src/app/models/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  userDto = new EditUserDTO();
  user!: UserResponse;
  constructor(private snackBar: MatSnackBar, private userService: UserService) { }

  ngOnInit(): void {
  }

  editUser() {
    if(this.userDto.fullName===""||this.userDto.username===""||
      this.userDto.email===""||this.userDto.email===""||this.userDto.phone==="") {
      this.snackBar.open('Faltan datos del usuario', 'Aceptar');
    }else{
      this.userService.addUser(this.userDto).subscribe(result => {
        this.user = result;
        this.snackBar.open('Se ha editado el usuario correctamente', 'Aceptar');
        history.go(0)
      });
    }
  }

}
