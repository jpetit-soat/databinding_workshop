package fr.soat.demo.moviesmodel.model;

import android.text.TextUtils;

import java.util.List;

import fr.soat.demo.moviesmodel.utils.StringUtils;
import fr.soat.demo.moviesmodel.dao.OMDBItem;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class MovieSeriesModel {

    private final PosterModel posterModel;

    private final String plot;
    private final String director;
    private final List<String> writers;
    private final int runtime;
    private final List<String> languages;
    private final List<String> country;
    private final String imdbID;
    private final float imdbRating;
    private final int imdbVotes;
    private final int metascore;
    private final int totalSeasons;


    public MovieSeriesModel(OMDBItem response) {
        posterModel = new PosterModel(response);

        writers = StringUtils.createList(response.Writer, ", ");
        plot = response.Plot;
        languages = StringUtils.createList(response.Language, ", ");
        country = StringUtils.createList(response.Country, ", ");
        imdbID = response.imdbID;

        if(!TextUtils.isEmpty(response.Director) && !response.Director.equalsIgnoreCase("N/A")){
            director = response.Director;
        } else {
            director = null;
        }

        // IMDb votes
        if(!TextUtils.isEmpty(response.imdbVotes) && !response.imdbVotes.equalsIgnoreCase("N/A")){
            String imdbVotesFormatted = response.imdbVotes.replace(",", "");
            imdbVotes = Integer.parseInt(imdbVotesFormatted);
        } else {
            imdbVotes = -1;
        }

        // IMDb Rating
        if(!TextUtils.isEmpty(response.imdbRating) && !response.imdbRating.equalsIgnoreCase("N/A")){
            imdbRating = Float.parseFloat(response.imdbRating);
        } else {
            imdbRating = -1;
        }

        // Metascore
        if(!TextUtils.isEmpty(response.Metascore) && !response.Metascore.equalsIgnoreCase("N/A")){
            metascore = Integer.parseInt(response.Metascore);
        } else {
            metascore = -1;
        }

        // Total Seasons
        if(!TextUtils.isEmpty(response.totalSeasons) && !response.totalSeasons.equalsIgnoreCase("N/A")){
            totalSeasons = Integer.parseInt(response.totalSeasons);
        } else {
            totalSeasons = -1;
        }

        // Runtime
        if(!TextUtils.isEmpty(response.Runtime) && !response.Runtime.equalsIgnoreCase("N/A")){
            String runtimeString = response.Runtime.replace(" min" ,"");
            runtime = Integer.parseInt(runtimeString);
        } else {
            runtime = -1;
        }
    }

    public PosterModel getPosterModel() {
        return posterModel;
    }

    public String getPlot() {
        return plot;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getWriters() {
        return writers;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getCountry() {
        return country;
    }

    public String getImdbID() {
        return imdbID;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public int getImdbVotes() {
        return imdbVotes;
    }

    public int getMetascore() {
        return metascore;
    }

    public int getTotalSeasons() {
        return totalSeasons;
    }
}
