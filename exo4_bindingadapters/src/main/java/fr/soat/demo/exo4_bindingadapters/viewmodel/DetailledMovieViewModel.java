package fr.soat.demo.exo4_bindingadapters.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import fr.soat.demo.exo4_bindingadapters.R;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;
import fr.soat.demo.moviesmodel.utils.DateUtils;
import fr.soat.demo.moviesmodel.utils.DrawableUtils;
import fr.soat.demo.moviesmodel.utils.PopupUtils;
import fr.soat.demo.moviesmodel.utils.StringUtils;

/**
 * Created by yann_huriez on 06/02/17.
 */

public class DetailledMovieViewModel {

    private final MovieSeriesModel movieModel;
    private final Context context;

    public String plot;
    public String director = null;
    public String writers = null;
    public @DrawableRes int countryDrawableRes;
    public String countryText;
    public String duration = null;
    public float rating = -1;

    public DetailledMovieViewModel(Context context, MovieSeriesModel movieModel) {
        this.context = context;
        this.movieModel = movieModel;

        init();
    }

    public PosterModel getPosterModel(){
        return movieModel.getPosterModel();
    }

    private void init(){
        // Plot
        plot = movieModel.getPlot();

        // Director
        if(movieModel.getDirector() != null){
            director = context.getString(R.string.format_director, movieModel.getDirector());
        }

        // Writers
        if (movieModel.getWriters() != null && !movieModel.getWriters().isEmpty()) {
            writers = context.getString(R.string.format_writers, StringUtils.assembleString(movieModel.getWriters(), ", "));
        }

        // Country
        String mainCountry = movieModel.getCountry() != null ? movieModel.getCountry().get(0) : null;
        countryDrawableRes = DrawableUtils.getCountryDrawable(mainCountry);
        if (countryDrawableRes > 0) {
            countryText = context.getString(R.string.format_country_with_drawable);
        } else {
            countryText = context.getString(R.string.format_country_with_param, mainCountry);
        }

        // Runtime
        int runtimeValue = movieModel.getRuntime();
        if (runtimeValue > 0) {
            String durationString = DateUtils.formatDuration(context, runtimeValue);
            duration = context.getString(R.string.format_duration, durationString);
        }

        // Rating
        if(movieModel.getImdbRating() >= 0) {
            rating = movieModel.getImdbRating() / 2.0f;
        }
    }

    public Drawable getCountryDrawable(){
        return ResourcesCompat.getDrawable(context.getResources(), countryDrawableRes, null);
    }

    public void onImdbClicked(View view){
        // This code show a popup below the view with the number of voting
        PopupUtils.showPopupForVotes(context, view, movieModel.getImdbVotes());
    }

}
