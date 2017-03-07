package fr.soat.demo.exo4_bindingadapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import fr.soat.demo.exo4_bindingadapters.databinding.ViewFilterAndDetailledMovieBinding;
import fr.soat.demo.exo4_bindingadapters.utils.MovieRatingView;
import fr.soat.demo.exo4_bindingadapters.viewmodel.DetailedMovieViewModel;
import fr.soat.demo.exo4_bindingadapters.viewmodel.FiltersViewModel;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.MovieRating;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;

public class MainActivity extends AppCompatActivity implements FilterListener {

    private MovieSeriesBusinessService movieSeriesBusinessService;
    private ViewFilterAndDetailledMovieBinding binding;

    private String lastFont = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.view_filter_and_detailled_movie);

        // Loading of the movie model
        movieSeriesBusinessService = new MovieSeriesBusinessService(this);

        binding.setFilterModel(new FiltersViewModel(this, this));
    }

    // This callback method comes from FilterViewModel.Listener
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onShowMovieSeries(List<MovieSeriesModel> movieOrSeriesModel) {
        DetailedMovieViewModel detailedMovieViewModel = new DetailedMovieViewModel(this, movieOrSeriesModel.get(0));

        binding.setMovieModel(detailedMovieViewModel);

        // // TODO Bonus If you feel at ease, you can do it using DataBinding
        // Used to hide the keyboard.
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        // The data contained in the ViewModel are scheduled to update the view before the next frame.
        // This method allows to update the view immediately, to make the changes from "updateViewWithDetails"
        // happens after the data binding.
        binding.executePendingBindings();

//        updateViewWithDetails(detailedMovieViewModel);
    }


    private void updateViewWithDetails(DetailedMovieViewModel movieOrSeriesModel) {
        View directorView = findViewById(R.id.detailed_movie_director);
        View durationView = findViewById(R.id.detailed_movie_duration);
        TextView plotView = (TextView) findViewById(R.id.detailed_movie_plot);
        View writersView = findViewById(R.id.detailed_movie_writers);

        setVisible(directorView, movieOrSeriesModel.director != null);
        setVisible(durationView, movieOrSeriesModel.duration != null);
        setVisible(plotView, movieOrSeriesModel.plot != null);
        setVisible(writersView, movieOrSeriesModel.writers != null);


        // Font change should be made only if there is a change in cultural type
        String plotFont = movieOrSeriesModel.getPlotFont();
        setFont(plotView, plotFont);

        RatingBar movieRatingBar = (RatingBar) findViewById(R.id.detailed_movie_imdb_rating);

        @ColorInt int starColor = ResourcesCompat.getColor(getResources(), R.color.star_color, null);
        setStarColor(movieRatingBar, starColor);

        MovieRatingView ratingView = (MovieRatingView) findViewById(R.id.detailed_movie_rating);
        MovieRating movieRating = movieOrSeriesModel.getMovieRating();
        if(movieRating != null) {
            ratingView.setRatingForAudienceAndAdvertising(movieRating);
        }

        ImageView posterView = (ImageView) findViewById(R.id.simple_movie_poster);
        PosterModel posterModel = movieOrSeriesModel.getPosterModel();

        Drawable emptyPosterDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_empty_poster, null);
        setPosterImage(posterView, posterModel, emptyPosterDrawable);
    }

    private void setVisible(View view, boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void setFont(TextView view, String newFont){
        if(lastFont == null && newFont == null){
            return;
        }

        if(lastFont == null || !lastFont.equals(newFont)) {
            lastFont = newFont;
            Typeface type = Typeface.createFromAsset(getAssets(), "fonts/" + lastFont + ".ttf");
            view.setTypeface(type);
        }
    }

    private void setStarColor(RatingBar ratingBar, @ColorInt int starColor){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(starColor, PorterDuff.Mode.SRC_ATOP);
    }

    private void setPosterImage(ImageView imageView, PosterModel posterModel, Drawable defaultView){
        if(posterModel != null && posterModel.getPosterUrl() != null){
            Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
            imageView.setImageDrawable(drawableFromPoster);
        } else {
            imageView.setImageDrawable(defaultView);
        }
    }
}