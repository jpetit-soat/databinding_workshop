package fr.soat.demo.moviesmodel.model;

import fr.soat.demo.moviesmodel.dao.OMDBItem;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class MovieSeriesModel {

    private final PosterModel posterModel;

    private final String runtime;
    private final String writers;
    private final String plot;
    private final String languages;
    private final String country;
    private final String awards;
    private final String imdbRating;
    private final String imdbVotes;
    private final String imdbID;
    private final String director;
    private final String metascore;
    private final String totalSeasons;


    public MovieSeriesModel(OMDBItem response) {
        posterModel = new PosterModel(response);
        runtime = response.Runtime;
        writers = response.Writer;
        plot = response.Plot;
        languages = response.Language;
        country = response.Country;
        awards = response.Awards;
        imdbRating = response.imdbRating;
        imdbVotes = response.imdbVotes;
        imdbID = response.imdbID;
        director = response.Director;
        metascore = response.Metascore;
        totalSeasons = response.totalSeasons;
    }

    public PosterModel getPosterModel() {
        return posterModel;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getWriters() {
        return writers;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguages() {
        return languages;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getDirector() {
        return director;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getTotalSeasons() {
        return totalSeasons;
    }
}
