import { HomePropietariosComponent } from './../components/pages/home-propietarios/home-propietarios.component';
import { SiteDetailComponent } from './../components/pages/site-detail/site-detail.component';
import { BackgroundComponent } from './../components/pages/background/background.component';
import { HomeComponent } from './../components/pages/home/home.component';
import { RegisterComponent } from './../components/pages/register/register.component';
import { LoginComponent } from './../components/pages/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from '../components/pages/page-not-found/page-not-found.component';

const routes: Routes = [
  { path: 'welcome', component: BackgroundComponent, data: {title: 'Bienvenido'}},
  { path: 'login', component: LoginComponent, data: {title: 'Iniciar Sesión'}},
  { path: 'register', component: RegisterComponent, data: {title: 'Regístrate'}},
  { path: 'home', component: HomeComponent, data: {title: 'Dashboard'}},
  { path: 'home/propietario', component: HomePropietariosComponent, data: {title: 'Mis Negocios'}},
  { path: 'site/:id', component: SiteDetailComponent, data: {title: 'Detalle del sitio'}},
  { path: '',   redirectTo: '/welcome', pathMatch: 'full'},
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
