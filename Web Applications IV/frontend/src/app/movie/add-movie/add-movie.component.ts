import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Movie } from '../movie/movie.model';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
  FormArray
} from '@angular/forms';
import { MovieDataService } from '../../dataservice/movie-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit {
  @Output() newMovie: EventEmitter<Movie> = new EventEmitter<Movie>();
  public movie: FormGroup;
  errorMsg: string;

  constructor(private fb: FormBuilder, private _movieDataService: MovieDataService, private router: Router) {
  }



  ngOnInit() {
    this.movie = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(1)]],
      rated: ['', [Validators.required, Validators.minLength(1)]],
      released: ['', [Validators.required, Validators.minLength(1)]],
      runtime: ['', [Validators.required, Validators.minLength(1), Validators.min(1), Validators.pattern("^[0-9]*$")]],
      genres: ['', [Validators.required, Validators.minLength(1)]],
      director: ['', [Validators.required, Validators.minLength(1)]], 
      actors: ['', [Validators.required, Validators.minLength(1)]],
      plot: ['', [Validators.required, Validators.minLength(20)]],
      languages: ['', [Validators.required, Validators.minLength(2)]],
      country: ['', [Validators.required, Validators.minLength(2)]],
      awards: ['', [Validators.required, Validators.minLength(1)]],
      poster: ['', [Validators.required, Validators.minLength(1)]],
      rating: ['', [Validators.required, Validators.minLength(1), Validators.min(0), Validators.max(10), Validators.pattern("^[0-9]*$")]],
      imdbid: ['', [Validators.required, Validators.minLength(1)]],
      type: ['', [Validators.required, Validators.minLength(1)]],
      opinion: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit() {

    if (this.movie.valid) {

      const movie = new Movie(this.movie.value.title, this.movie.value.rated,
        this.movie.value.released, this.movie.value.runtime, this.movie.value.genres, this.movie.value.director,
        localStorage.getItem('user'), this.movie.value.actors, this.movie.value.plot, this.movie.value.languages,
        this.movie.value.country, this.movie.value.awards, this.movie.value.poster, this.movie.value.rating,
        this.movie.value.imdbid, this.movie.value.type, this.movie.value.opinion);

      console.log("in add movie");
      console.log(movie);

      this.newMovie.emit(movie);

      //reload page so input is empty
      window.location.reload();
    } else {
      let warning = "Something went wrong. Please check if the following data is correct: ";
      let controlls = this.movie.controls;
      for (let name in controlls) {
        if(controlls[name].invalid)
        {
          warning += `
          ` + name;
        }
      }
      alert(warning);
    }
  }
}
