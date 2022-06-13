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
      fullName: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      verifyEmail: new FormControl('', [Validators.required, Validators.email]),
      birthDate: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]),
      verifyPassword: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(20)])
    })
  }

  registerPropietario() {
    this.registerDto.fullName = this.registerForm.get('fullName')?.value;
    this.registerDto.username = this.registerForm.get('username')?.value;
    this.registerDto.email = this.registerForm.get('email')?.value;
    this.registerDto.verifyEmail = this.registerForm.get('verifyEmail')?.value;
    this.registerDto.birthDate = this.registerForm.get('birthDate')?.value;
    this.registerDto.phone = this.registerForm.get('phone')?.value;
    this.registerDto.password = this.registerForm.get('password')?.value;
    this.registerDto.verifyPassword = this.registerForm.get('verifyPassword')?.value;
    this.authService.registerPropietario(this.registerDto).subscribe(res => {
      this.router.navigate(['/login']);
    });
  }

  registerAdmin() {
    this.registerDto.fullName = this.registerForm.get('fullName')?.value;
    this.registerDto.username = this.registerForm.get('username')?.value;
    this.registerDto.email = this.registerForm.get('email')?.value;
    this.registerDto.verifyEmail = this.registerForm.get('verifyEmail')?.value;
    this.registerDto.birthDate = this.registerForm.get('birthDate')?.value;
    this.registerDto.phone = this.registerForm.get('phone')?.value;
    this.registerDto.password = this.registerForm.get('password')?.value;
    this.registerDto.verifyPassword = this.registerForm.get('verifyPassword')?.value;
    this.authService.registerAdmin(this.registerDto).subscribe(res => {
      this.router.navigate(['/login']);
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

}
