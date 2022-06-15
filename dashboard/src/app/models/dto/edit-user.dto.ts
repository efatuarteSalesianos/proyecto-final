export class EditUserDTO {
  fullName: string;
  username:	string;
  email:	string;
  birthDate: Date;
  phone: string;
  avatar: string;

  constructor() {
    this.fullName = '';
    this.username = '';
    this.email = '';
    this.birthDate = new Date();
    this.phone = '';
    this.avatar = '';
  }
}
