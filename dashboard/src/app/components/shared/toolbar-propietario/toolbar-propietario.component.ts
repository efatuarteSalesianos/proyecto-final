import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toolbar-propietario',
  templateUrl: './toolbar-propietario.component.html',
  styleUrls: ['./toolbar-propietario.component.css']
})
export class ToolbarPropietarioComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  getUsername () {
    return localStorage.getItem('usernamePropietario')
  }

  getAvatar() {
    return localStorage.getItem('avatarPropietario')
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  };


}
