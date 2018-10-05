import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './PageNotFound/page-not-found.component';
import { LoginComponent } from './user/login/login.component';
import { HttpModule } from '@angular/http';
import { RegisterComponent } from './user/register/register.component';
import { CommonModule } from '@angular/common';
import { AuthenticationService } from './user/authentication.service';
import { LogoutComponent } from './user/logout/logout.component';
import { AuthGuardService } from './user/auth-guard.service';
import { MovieListComponent } from './movie/movie-list/movie-list.component';
import { AddNewMovieComponent } from './movie/add-new-movie/add-new-movie.component';
import { MovieComponent } from './movie/movie/movie.component';
import { AddMovieComponent } from './movie/add-movie/add-movie.component';
import { MovieFilterPipe } from './pipe/movie-filter.pipe';
import { NavbarComponent } from './navbar/navbar.component';
import { basehttpInterceptorProviders } from './http-interceptors';
import { MyMoviesComponent } from './movie/my-movies/my-movies.component';
import { MyMoviesFilterPipe } from './pipe/my-movies-filter.pipe';


const appRoutes: Routes = [
  { path: 'movie-list', component: MovieListComponent },
  { path: 'add-movie',
    canActivate: [AuthGuardService],
    component: AddNewMovieComponent},
    { path: 'my-movie',
    canActivate: [AuthGuardService],
    component: MyMoviesComponent},
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'movie-list', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    MovieComponent,
    PageNotFoundComponent,
    MovieListComponent,
    MovieFilterPipe,
    AddMovieComponent,
    AddNewMovieComponent,
    NavbarComponent,
    MyMoviesComponent,
    MyMoviesFilterPipe
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)],
  providers: [basehttpInterceptorProviders, AuthenticationService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
