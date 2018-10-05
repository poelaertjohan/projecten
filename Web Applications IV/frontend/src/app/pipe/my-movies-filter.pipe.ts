import { Pipe, PipeTransform } from '@angular/core';
import { Movie } from '../movie/movie/movie.model';

@Pipe({
  name: 'myMoviesFilter'
})
export class MyMoviesFilterPipe implements PipeTransform {

  transform(movies: Movie[], search: string): Movie[] {
    if(movies != null)
    {
      return movies.filter(mov =>
        mov.writer.toLowerCase() == (search.toLowerCase())
      );
    } else {
      return movies;
    }
  }
}
