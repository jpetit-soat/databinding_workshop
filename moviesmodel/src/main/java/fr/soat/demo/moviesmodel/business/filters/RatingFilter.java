package fr.soat.demo.moviesmodel.business.filters;

import android.text.TextUtils;

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
        String imdbRating = model.getImdbRating();
        if(!TextUtils.isEmpty(imdbRating) && !imdbRating.equalsIgnoreCase("N/A")){
            float floatRating = Float.valueOf(imdbRating);
            return floatRating >= rating;
        }
        return false;
    }
}
