import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import {LogoutComponent} from "./components/logout/logout.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoggedInAuthGuard} from "./guard/logged-in.guard";
import {AuthGuard} from "./guard/auth.guard";
import {MainComponent} from "./components/main/main.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent , canActivate:[LoggedInAuthGuard]},
  { path: 'register', component: RegisterComponent , canActivate:[LoggedInAuthGuard]},
  { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard]},
  { path: 'main', component: MainComponent, canActivate: [AuthGuard]},
  { path: '', component: LoginComponent, canActivate:[LoggedInAuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
