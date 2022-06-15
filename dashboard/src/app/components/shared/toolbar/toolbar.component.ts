import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  getUsername () {
    return localStorage.getItem('username')
  }

  setUrl() {
    if (localStorage.getItem('Rol') === 'admin') {
      return '/home';
    } else {
      return '/home/propietario';
    }
  }

  getAvatar() {
    return localStorage.getItem('avatar')
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  };

}
