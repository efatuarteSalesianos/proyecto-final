import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterDTO } from 'src/app/models/dto/register.dto';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;
  registerDto = new RegisterDTO();

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      birthDate: new FormControl('', [Validators.required]),
      privateAccount: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      password2: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)])
    })
  }

  register() {
    this.registerDto.username = this.registerForm.get('username')?.value;
    this.registerDto.email = this.registerForm.get('email')?.value;
    this.registerDto.birthDate = this.registerForm.get('birthDate')?.value;
    this.registerDto.privateAccount = this.registerForm.get('privateAccount')?.value;
    this.registerDto.password = this.registerForm.get('password')?.value;
    this.registerDto.password2 = this.registerForm.get('password2')?.value;
    this.authService.register(this.registerDto).subscribe(res => {
      this.router.navigate(['/login']);
    });
  }

}
