export class RegisterDTO {
  fullName: string;
  username:	string;
  email:	string;
  verifyEmail: string;
  birthDate: Date;
  phone: string;
  password:	string;
  verifyPassword: string;

  constructor() {
    this.fullName = '';
    this.username = '';
    this.email = '';
    this.verifyEmail = '';
    this.birthDate = new Date();
    this.phone = '';
    this.password = '';
    this.verifyPassword = '';
  }
}
