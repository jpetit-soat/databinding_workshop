package fr.soat.demo.exo2_viewmodel;

import android.content.Context;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 06/02/17.
 */

public class DetailedMovieViewModel {

    private final MovieSeriesModel movieModel;
    private final Context context;

    public DetailedMovieViewModel(Context context, MovieSeriesModel movieModel) {
        this.context = context;
        this.movieModel = movieModel;
    }

    // TODO Create methods here to allow the layout to manipulate data


}
