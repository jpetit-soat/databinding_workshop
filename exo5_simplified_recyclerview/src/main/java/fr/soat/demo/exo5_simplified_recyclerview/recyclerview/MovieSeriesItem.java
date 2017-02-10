package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import fr.soat.demo.exo5_simplified_recyclerview.FilterListener;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class MovieSeriesItem {

    public static final int TYPE_FILTER = 0;
    public static final int TYPE_TTILE = 1;
    public static final int TYPE_DETAILED_MOVIE = 2;
    public static final int TYPE_POSTER = 3;

    private PosterModel posterModel;
    private String title;
    private MovieSeriesModel movieSeriesModel;
    private FilterListener filterListener;

    private int itemType;

    public static MovieSeriesItem createFilterItem(FilterListener listener){
        MovieSeriesItem result = new MovieSeriesItem(TYPE_FILTER);
        result.filterListener = listener;
        return result;
    }

    public static MovieSeriesItem createTitleItem(String title){
        MovieSeriesItem result = new MovieSeriesItem(TYPE_TTILE);
        result.title = title;
        return result;
    }

    public static MovieSeriesItem createDetailedMovieItem(MovieSeriesModel movieSeriesModel){
        MovieSeriesItem result = new MovieSeriesItem(TYPE_DETAILED_MOVIE);
        result.movieSeriesModel = movieSeriesModel;
        return result;
    }

    public static MovieSeriesItem createPosterItem(PosterModel posterModel){
        MovieSeriesItem result = new MovieSeriesItem(TYPE_POSTER);
        result.posterModel = posterModel;
        return result;
    }


    // Constructor and getters

    private MovieSeriesItem(int itemType) {
        this.itemType = itemType;
    }

    public PosterModel getPosterModel() {
        return posterModel;
    }

    public String getTitle() {
        return title;
    }

    public MovieSeriesModel getMovieSeriesModel() {
        return movieSeriesModel;
    }

    public int getItemType() {
        return itemType;
    }

    public FilterListener getFilterListener() {
        return filterListener;
    }
}
