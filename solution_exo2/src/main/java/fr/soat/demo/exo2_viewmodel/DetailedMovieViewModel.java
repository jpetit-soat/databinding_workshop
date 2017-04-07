package fr.soat.demo.exo2_viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;
import fr.soat.demo.moviesmodel.utils.DateUtils;
import fr.soat.demo.moviesmodel.utils.DrawableUtils;

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

    public PosterModel getPosterModel(){
        return movieModel.getPosterModel();
    }

    public String getPlot() {
        return movieModel.getPlot();
    }

    public String getDirector() {
        if(movieModel.getDirector() != null){
            return context.getString(R.string.format_director, movieModel.getDirector());
        }
        return null;
    }

    public String getDuration() {
        int runtimeValue = movieModel.getRuntime();
        if (runtimeValue > 0) {
            String durationString = DateUtils.formatDuration(context, runtimeValue);
            return context.getString(R.string.format_duration, durationString);
        }
        return null;
    }

    public Drawable getCountryDrawable(){
        String mainCountry = movieModel.getCountry().get(0);
        @DrawableRes int countryDrawableRes = DrawableUtils.getCountryDrawable(mainCountry);
        return ResourcesCompat.getDrawable(context.getResources(), countryDrawableRes, null);
    }
}
