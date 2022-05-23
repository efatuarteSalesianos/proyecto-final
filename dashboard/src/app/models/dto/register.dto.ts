export class RegisterDTO {
  username:	string;
  email:	string;
  birthDate: Date;
  privateAccount: boolean;
  password:	string;
  password2: string;

  constructor() {
    this.username = '';
    this.email = '';
    this.birthDate = new Date();
    this.privateAccount = false;
    this.password = '';
    this.password2 = '';
  }
}
