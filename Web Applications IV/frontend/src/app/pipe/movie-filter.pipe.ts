import { Pipe, PipeTransform } from '@angular/core';
import { Movie } from '../movie/movie/movie.model';
@Pipe({
  name: 'movieFilter'
})
export class MovieFilterPipe implements PipeTransform {

  transform(movies: Movie[], search: string): Movie[] {
    if (!search || search.length === 0) {
      return movies;
    }

    var filterResult = new Array<Movie>();
    filterResult = movies.filter(mov =>
      mov.title.toLowerCase().includes(search.toLowerCase()) || mov.actors.toLowerCase().includes(search.toLowerCase())
      || mov.genres.toLowerCase().includes(search.toLowerCase()) || mov.type.toLowerCase() == (search.toLowerCase())
      || mov.director.toLowerCase().startsWith(search.toLowerCase())
    );

    return filterResult;
  }
}
