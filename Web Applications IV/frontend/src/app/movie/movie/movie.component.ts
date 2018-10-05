import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Movie } from './movie.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MovieDataService } from '../../dataservice/movie-data.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  @Input() public movie: Movie;
  showImage = false;

  constructor(private fb: FormBuilder, private _movieDataService: MovieDataService) {
  }

  ngOnInit() {
    this.showImage = window.location.href.includes("my-movie");
  }
}
