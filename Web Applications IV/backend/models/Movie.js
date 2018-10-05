let mongoose = require('mongoose');

let MovieSchema = new mongoose.Schema({
  title: String,
  rated: String,
  released: String,
  runtime: String,
  genres: String,
  director: String,
  writer: String,
  actors: String,
  plot: String,
  languages: String,
  country: String,
  awards: String,
  poster: String,
  rating: String,
  imdbid: String,
  type: String,
  review: String,
  username: String,
});

mongoose.model('Movie', MovieSchema);