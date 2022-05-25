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
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialImportsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule
  ],
  providers: [Title],
  bootstrap: [AppComponent]
})
export class AppModule { }
