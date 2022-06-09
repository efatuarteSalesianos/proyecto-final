import { Time } from "@angular/common";

export class SiteDTO {
  name: string;
  description:	string;
  address:	string;
  city: string;
  postalCode: string;
  email: string;
  phone: string;
  web: string;
  openingHour: Date;
  closingHour: Date;
  type: string
  propietarioId: number;

  constructor() {
    this.name = '';
    this.description = '';
    this.address = '';
    this.city = '';
    this.postalCode = '';
    this.email = '';
    this.phone = '';
    this.web = '';
    this.openingHour = new Date();
    this.closingHour = new Date();
    this.type = '';
    this.propietarioId = 0;
  }
}
