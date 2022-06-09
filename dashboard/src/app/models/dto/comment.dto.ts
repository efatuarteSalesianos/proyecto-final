export class CommentDTO {
  title: string;
  description:	string;
  rate:	number;
  image: string;

  constructor() {
    this.title = '';
    this.description = '';
    this.rate = 0;
    this.image = '';
  }
}
