package fr.soat.demo.moviesmodel.business.filters;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class RatingFilter extends AbstractMovieSeriesFilter {

    private float rating;

    public RatingFilter(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean isMatchingFilter(MovieSeriesModel model) {
        float imdbRating = model.getImdbRating();
        return imdbRating >= rating;
    }
}
