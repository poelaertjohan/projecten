import { Component } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { debounceTime, distinct, distinctUntilChanged, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterModule, Routes, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './user/authentication.service';
import { Movie } from './movie/movie/movie.model';
import { MovieDataService } from './dataservice/movie-data.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MovieDataService]
})

export class AppComponent {
  private title: String = 'movies';
  filterMovieName: string;
  private filterMovie$ = new Subject<string>();
  private _movies: Movie[];
  public errorMsg: string;

  constructor(private _movieDataService: MovieDataService, private router: Router) {
  }


  ngOnInit() {
    this._movieDataService.movies.subscribe(
      data => { this._movies = data; console.log(data) },
      (error: HttpErrorResponse) => {
        this.errorMsg = `Can't load movies`;
      }
    );
  }
}
