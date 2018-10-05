
    export class Movie {
        private _id: string;
        private _title: string;
        private _rated: string;
        private _released: string;
        private _runtime: string;
        private _genres: string;
        private _director: string;
        private _writer: string;
        private _actors: string;
        private _plot: string;
        private _languages: string;
        private _country: string;
        private _awards: string;
        private _poster: string;
        private _rating: string;
        private _imdbid: string;
        private _type: string;
        private _review: string;

        constructor(title: string, rated: string, released: string, runtime: string,
            genres: string, director: string, writer: string, actors: string, plot: string,
            languages: string, country: string, awards: string, poster: string,
            rating: string, imdbid: string, type: string, review: string) {
            this._title = title;
            this._rated = rated;
            this._released = released;
            this._runtime = runtime;
            this._genres = genres;
            this._director = director;
            this._writer = writer;
            this._actors = actors;
            this._plot = plot;
            this._languages = languages;
            this._country = country;
            this._awards = awards;
            this._poster = poster;
            this._rating = rating;
            this._imdbid = imdbid;
            this._type = type;
            this._review = review;
        }

        get id(): string {
            return this._id;
        }

        get title(): string {
            return this._title;
        }

        set title(s: string) {
            this._title = s;
        }

        get rated(): string {
            return this._rated;
        }

        set rated(s: string) {
            this._rated = s;
        }

        get released(): string {
            return this._released;
        }

        set released(s: string) {
            this._released = s;
        }

        get runtime(): string {
            return this._runtime;
        }

        set runtime(s: string) {
            this._runtime = s;
        }

        get genres(): string {
            return this._genres;
        }

        set genres(s: string) {
            this._genres = s;
        }

        get director(): string {
            return this._director;
        }

        set director(s: string) {
            this._director = s;
        }

        get writer(): string {
            return this._writer;
        }

        get actors(): string {
            return this._actors;
        }

        set actors(s: string) {
            this._actors = s;
        }

        get plot(): string {
            return this._plot;
        }

        set plot(s: string) {
            this._plot = s;
        }

        get languages(): string {
            return this._languages;
        }

        set languages(s: string) {
            this._languages = s;
        }

        get country(): string {
            return this._country;
        }

        set country(s: string) {
            this._country = s;
        }

        get awards(): string {
            return this._awards;
        }

        set awards(s: string) {
            this._awards = s;
        }

        get poster(): string {
            return this._poster;
        }

        set poster(s: string) {
            this._poster = s;
        }

        get rating(): string {
            return this._rating;
        }

        set rating(s: string) {
            this._rating = s;
        }

        get imdbid(): string {
            return this._imdbid;
        }

        set imdbid(s: string) {
            this._imdbid = s;
        }

        get type(): string {
            return this._type;
        }

        set type(s: string) {
            this._type = s;
        }

        get review(): string {
            return this._review;
        }

        set review(s: string) {
            this._review = s;
        }

        static fromJSON(json: any): Movie {
            const mov = new Movie(
                json.title,
                json.rated,
                json.released,
                json.runtime,
                json.genres,
                json.director,
                json.writer,
                json.actors,
                json.plot,
                json.languages,
                json.country,
                json.awards,
                json.poster,
                json.rating,
                json.imdbid,
                json.type,
                json.review
            );
            mov._id = json._id;
            return mov;
        }

        toJSON() {
            return {
                _id: this._id,
                title: this._title,
                rated: this._rated,
                released: this._released,
                runtime: this._runtime,
                genres: this._genres,
                director: this._director,
                writer: this._writer,
                actors: this._actors,
                plot: this._plot,
                languages: this._languages,
                country: this._country,
                awards: this._awards,
                poster: this._poster,
                rating: this._rating,
                imdbid: this._imdbid,
                type: this._type,
                review: this._review
            };
        }
    }
