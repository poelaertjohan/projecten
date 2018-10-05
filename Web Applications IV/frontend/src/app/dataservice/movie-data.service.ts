import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../movie/movie/movie.model';

@Injectable()
export class MovieDataService {
  private readonly _appUrl = '/API';

  constructor(private http: HttpClient) {
  }

  get movies(): Observable<Movie[]> {
    return this.http
      .get(`${this._appUrl}/movies/`)
      .pipe(map((list: any[]): Movie[] => list.map(Movie.fromJSON)));
  }

  addMovie(movie: Movie): Observable<Movie> {
    return this.http
      .post(`${this._appUrl}/movies/`, movie)
      .pipe(map(Movie.fromJSON));
  }

  deleteMovie(mov) {
    return this.http
      .delete(`${this._appUrl}/movie/${mov.id}`)
      .pipe(map(Movie.fromJSON));
  }

  getMovie(id: string) {
    const theUrl = `${this._appUrl}/movie/${id}`;
    return this.http.get(theUrl).pipe(map(Movie.fromJSON));
  }
}