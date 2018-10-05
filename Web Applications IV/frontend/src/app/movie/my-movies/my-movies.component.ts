import { Component, OnInit } from '@angular/core';
import { MovieDataService } from '../../dataservice/movie-data.service';
import { Movie } from '../movie/movie.model';
import { Subject } from 'rxjs/Subject';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-my-movies',
  templateUrl: './my-movies.component.html',
  styleUrls: ['./my-movies.component.css']
})
export class MyMoviesComponent implements OnInit {

  private _movies: Movie[];
  public errorMsg: string;
  filterMovie$ = new Subject<string>();
  filterMovie: string;

  constructor(private _movieDataService: MovieDataService) {
  }

  ngOnInit() {
    this.filterMovie = localStorage.getItem('user');

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

  deleteMovie(movie) {
    if (confirm("Delete " + movie.title + "?")) {
      this._movieDataService.deleteMovie(movie).subscribe(
        item => (this._movies = this._movies.filter(val => item.id !== val.id))
      );
    }
  }

  applyFilter(filter: string) {
    this.filterMovie = filter;
  }

}


