import { Component, OnInit } from '@angular/core';
import { Movie } from '../movie/movie.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { MovieDataService } from '../../dataservice/movie-data.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  private _movies: Movie[];
  public errorMsg: string;
  filterMovie$ = new Subject<string>();
  filterMovieTitle: string;

  constructor(private _movieDataService: MovieDataService) {
  }

  ngOnInit() {
    this.filterMovie$
    .pipe(
      debounceTime(400),
      distinctUntilChanged(),
      map(val => val.toLowerCase())
    )
    .subscribe(val => (this.filterMovieTitle = val));
   

    this._movieDataService.movies.subscribe(
      data => { this._movies = data },
      (error: HttpErrorResponse) => {
        this.errorMsg = `Can't load movies. Please come back later.`;
      }
    );
  }

  get movies() {
    return this._movies;
  }

  applyFilter(filter: string) {
    this.filterMovieTitle = filter;
  }

}
