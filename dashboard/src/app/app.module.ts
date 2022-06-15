import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { AppRoutingModule } from './modules/app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/pages/login/login.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { HomeComponent } from './components/pages/home/home.component';
import { DeleteSiteDialogComponent } from './components/dialogs/delete-site-dialog/delete-site-dialog.component';
import { GiveAdminDialogComponent } from './components/dialogs/give-admin-dialog/give-admin-dialog.component';
import { MaterialImportsModule } from './modules/material-imports.module';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { PageNotFoundComponent } from './components/pages/page-not-found/page-not-found.component';
import { BackgroundComponent } from './components/pages/background/background.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SitesComponent } from './components/items/sites/sites.component';
import { UsersComponent } from './components/items/users/users.component';
import { ToolbarComponent } from './components/shared/toolbar/toolbar.component';
import { DeleteUserDialogComponent } from './components/dialogs/delete-user-dialog/delete-user-dialog.component';
import { DeleteCommentDialogComponent } from './components/dialogs/delete-comment-dialog/delete-comment-dialog.component';
import { DeleteAppointmentDialogComponent } from './components/dialogs/delete-appointment-dialog/delete-appointment-dialog.component';
import { CommentsComponent } from './components/items/comments/comments.component';
import { AppointmentsComponent } from './components/items/appointments/appointments.component';
import { SiteDetailComponent } from './components/pages/site-detail/site-detail.component';
import { AddSiteDialogComponent } from './components/dialogs/add-site-dialog/add-site-dialog.component';
import { AddUserDialogComponent } from './components/dialogs/add-user-dialog/add-user-dialog.component';
import { EditSiteComponent } from './components/dialogs/edit-site/edit-site.component';
import { EditUserComponent } from './components/dialogs/edit-user/edit-user.component';
import { HomePropietariosComponent } from './components/pages/home-propietarios/home-propietarios.component';
import { SiteGridComponent } from './components/items/site-grid/site-grid.component';
import { StarRatingModule } from 'angular-star-rating';
import { registerLocaleData } from '@angular/common';
import { MatNativeDateModule } from '@angular/material/core';
import localeES from '@angular/common/locales/es';

registerLocaleData(localeES, 'es');

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    DeleteSiteDialogComponent,
    GiveAdminDialogComponent,
    PageNotFoundComponent,
    BackgroundComponent,
    SitesComponent,
    UsersComponent,
    ToolbarComponent,
    DeleteUserDialogComponent,
    DeleteCommentDialogComponent,
    DeleteAppointmentDialogComponent,
    CommentsComponent,
    AppointmentsComponent,
    SiteDetailComponent,
    AddSiteDialogComponent,
    AddUserDialogComponent,
    EditSiteComponent,
    EditUserComponent,
    HomePropietariosComponent,
    SiteGridComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialImportsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatNativeDateModule,
    StarRatingModule.forRoot()
  ],

  providers: [Title, { provide: localeES, useValue: 'es' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
