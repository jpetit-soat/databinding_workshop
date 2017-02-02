package fr.soat.demo.moviesmodel.business;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.dao.OMDBDao;
import fr.soat.demo.moviesmodel.dao.OMDBItem;
import fr.soat.demo.moviesmodel.dao.OMDBSearchResponse;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class MovieSeriesBusinessService {

    private OMDBDao omdbDao;
    private Context context;

    public MovieSeriesBusinessService(Context context) {
        this.context = context;
    }

    public List<MovieSeriesModel> getAllMovieSeries() {
        List<MovieSeriesModel> result = new ArrayList<>();
        OMDBSearchResponse response = getOMDBDao().localSearch(context);
        for (OMDBItem omdbItem : response.Search) {
            result.add(new MovieSeriesModel(omdbItem));
        }
        return result;
    }

    public List<MovieSeriesModel> getFilteredMovieSeries(List<AbstractMovieSeriesFilter> filters) {
        List<MovieSeriesModel> allMoviesAndTVShows = getAllMovieSeries();
        if(filters == null || filters.isEmpty()){
            return allMoviesAndTVShows;
        }

        List<MovieSeriesModel> result = allMoviesAndTVShows;
        for (AbstractMovieSeriesFilter filter : filters) {
            result = filter.matchingFilters(result);
        }

        return result;
    }

    public PosterModel getPosterModelFromName(String name) {
        OMDBSearchResponse response = getOMDBDao().localSearch(context);
        for (OMDBItem omdbItem : response.Search) {
            if (omdbItem.Title.equalsIgnoreCase(name)) {
                return new PosterModel(omdbItem);
            }
        }
        return null;
    }

    public MovieSeriesModel getMovieSeriesModelFromName(String name) {
        OMDBSearchResponse response = getOMDBDao().localSearch(context);
        for (OMDBItem omdbItem : response.Search) {
            if (omdbItem.Title.equalsIgnoreCase(name)) {
                return new MovieSeriesModel(omdbItem);
            }
        }
        return null;
    }

    public Drawable getDrawableFromPoster(PosterModel posterModel){
        if(posterModel.getPosterUrl() != null){
            return getOMDBDao().getPosterDrawable(context, posterModel.getPosterUrl());
        }
        return null;
    }



    public OMDBDao getOMDBDao() {
        if (omdbDao == null) {
            omdbDao = new OMDBDao();
        }
        return omdbDao;
    }
}
