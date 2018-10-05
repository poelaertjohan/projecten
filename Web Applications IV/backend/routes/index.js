var express = require('express');
var router = express.Router();
var mongo = require('mongodb');
var assert = require('assert');

let mongoose = require('mongoose');
let Movie = mongoose.model('Movie');

router.get('/API/movies/', function (req, res, next) {
  let query = Movie.find().populate('movies');
  query.exec(function (err, movies) {
    if (err) {
      return next(err);
    }
    res.json(movies);
  });
});


router.post('/API/movies/', function (req, res, next) {
  Movie.create(function (err, ings) {
    if (err) {
      return next(err);
    }
    let movie = new Movie({
      title: req.body.title, year: req.body.year, rated: req.body.rated,
      released: req.body.released, runtime: req.body.runtime, genres: req.body.genres, director: req.body.director,
      writer: req.body.writer, actors: req.body.actors, plot: req.body.plot, languages: req.body.languages,
      country: req.body.country, awards: req.body.awards, poster: req.body.poster, rating: req.body.rating,
      imdbid: req.body.imdbid, type: req.body.type, review: req.body.review
    });

    movie.save(function (err, rec) {
      if (err) {
        return next(err);
      }

      res.json(rec);
    });
  });
});


router.get('/API/movie/:movie', function (req, res, next) {
  res.json(req.movie);
});


router.delete('/API/movie/:movie', function (req, res) {
  req.movie.remove(function (err) {
    if (err) {
      return next(err);
    }
    res.json(req.movie);
  });
});

router.param('movie', function (req, res, next, id) {
  let query = Movie.findById(id);
  query.exec(function (err, movie) {
    if (err) {
      return next(err);
    }
    if (!movie) {
      return next(new Error('not found ' + id));
    }
    req.movie = movie;
    return next();
  });
});

router.post('/API/movie/:movie', function (req, res, next) {
  req.movie.save(function (err, rec) {
    if (err) return next(err);
  });
});

module.exports = router;