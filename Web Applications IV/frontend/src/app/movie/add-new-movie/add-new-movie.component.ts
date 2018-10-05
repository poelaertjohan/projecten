import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Movie } from '../movie/movie.model';
import { HttpErrorResponse } from '@angular/common/http';
import { MovieDataService } from '../../dataservice/movie-data.service';

@Component({
  selector: 'app-add-new-movie',
  templateUrl: './add-new-movie.component.html',
  styleUrls: ['./add-new-movie.component.css']
})
export class AddNewMovieComponent implements OnInit {

  private _movies: Movie[];
  public errorMsg: string;
  constructor(private fb: FormBuilder, private _movieDataService: MovieDataService) {
  }

  ngOnInit() {
    this._movieDataService.movies.subscribe(
      data => { this._movies = data; },
      (error: HttpErrorResponse) => {
        this.errorMsg = `Can't load movies`;
      }
    );
  }


  addMovie(movie: Movie) {
    this._movieDataService.addMovie(movie).subscribe(
      item => this._movies.push(item), 
      (error: HttpErrorResponse) => {
        this.errorMsg = `Can't add movie`;
      }
    );
  }
}
