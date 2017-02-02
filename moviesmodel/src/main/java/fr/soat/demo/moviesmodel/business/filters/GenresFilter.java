package fr.soat.demo.moviesmodel.business.filters;

import java.util.List;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class GenresFilter extends AbstractMovieSeriesFilter {

    private List<String> genres;

    public GenresFilter(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public boolean isMatchingFilter(MovieSeriesModel model) {
        List<String> modelGenres = model.getPosterModel().getGenres();
        if (modelGenres == null || modelGenres.isEmpty()) {
            return false;
        }

        for (String genre : modelGenres) {
            for (String filterGenre : genres) {
                if (!genre.toLowerCase().equalsIgnoreCase(filterGenre.toLowerCase())){
                    return false;
                }
            }
        }

        return true;
    }
}
