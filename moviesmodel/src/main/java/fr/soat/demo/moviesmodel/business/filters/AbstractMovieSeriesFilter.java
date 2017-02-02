package fr.soat.demo.moviesmodel.business.filters;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public abstract class AbstractMovieSeriesFilter {

    protected abstract boolean isMatchingFilter(MovieSeriesModel model);

    public List<MovieSeriesModel> matchingFilters(List<MovieSeriesModel> models) {
        List<MovieSeriesModel> result = new ArrayList<>();
        for (MovieSeriesModel model : models) {
            if(isMatchingFilter(model)){
                result.add(model);
            }
        }
        return result;
    }
}
