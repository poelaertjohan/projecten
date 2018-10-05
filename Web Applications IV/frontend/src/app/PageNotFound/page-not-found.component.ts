import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    /*   setTimeout((router: Router) => {
         this.router.navigate(['app-movie']);
       }, 5000);  //5s*/

    setTimeout((router: Router) => {
      this.router.navigate(['movie-list']);
    }, 10000);

  }

}
