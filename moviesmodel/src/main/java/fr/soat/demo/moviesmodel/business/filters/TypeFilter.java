package fr.soat.demo.moviesmodel.business.filters;

import fr.soat.demo.moviesmodel.model.CulturalType;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class TypeFilter extends AbstractMovieSeriesFilter {

    private CulturalType type;

    public TypeFilter(CulturalType type) {
        this.type = type;
    }

    @Override
    public boolean isMatchingFilter(MovieSeriesModel model) {
        return model.getPosterModel().getType() == type;
    }
}
