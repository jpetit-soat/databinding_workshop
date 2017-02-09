package fr.soat.demo.exo4_bindingadapters;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.soat.demo.exo4_bindingadapters.databinding.ViewFilterAndDetailledMovieBinding;
import fr.soat.demo.exo4_bindingadapters.viewmodel.DetailledMovieViewModel;
import fr.soat.demo.exo4_bindingadapters.viewmodel.FiltersViewModel;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.CulturalType;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.utils.MovieRatingView;

public class MainActivity extends AppCompatActivity implements FiltersViewModel.Listener {

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
    @Override
    public void onShowMovieSeries(MovieSeriesModel movieOrSeriesModel) {
        DetailledMovieViewModel detailledMovieViewModel = new DetailledMovieViewModel(this, movieOrSeriesModel);

        binding.setMovieModel(detailledMovieViewModel);

        // TODO The code contained in this method can entirely be replace with BindingAdapters
        updateViewWithDetails(movieOrSeriesModel);
    }


    private void updateViewWithDetails(MovieSeriesModel movieOrSeriesModel) {
        // TODO Here, we hide views that may don't have their relative value. Must be changed by a BindingAdapter
        View directorView = findViewById(R.id.detailed_movie_director);
        View durationView = findViewById(R.id.detailed_movie_duration);
        TextView plotView = (TextView) findViewById(R.id.detailed_movie_plot);
        View writersView = findViewById(R.id.detailed_movie_writers);

        setVisible(directorView, movieOrSeriesModel.getDirector() != null);
        setVisible(durationView, movieOrSeriesModel.getRuntime()< 0);
        setVisible(plotView, movieOrSeriesModel.getPlot() != null);
        setVisible(writersView, movieOrSeriesModel.getWriters().isEmpty());


        // TODO Use a BindingAdapter to do this font switch directly inside the XML.
        // Font change should be made only if there is a change in cultural type
        CulturalType newType = movieOrSeriesModel.getPosterModel().getType();
        if (newType == CulturalType.SERIES) {
            setFont(plotView, "helvetica_normal");
        } else {
            setFont(plotView, "roboto_condensed_light");
        }


        // TODO Use a BindingAdapter to pass the star color res through the XML
        RatingBar movieRatingBar = (RatingBar) findViewById(R.id.detailed_movie_imdb_rating);
        RatingBar filterRatingBar = (RatingBar) findViewById(R.id.view_filter_imdb_rating);

        @ColorRes int starColor = R.color.star_color;

        setStarColor(movieRatingBar, starColor);
        setStarColor(filterRatingBar, starColor);


        // TODO Call this custom view setter from the XML. You can do it using BindingAdapter, or guess the attribute name based on the setter.
        // TODO Bonus : rename this setter attribute using "BindingMethods"
        if(movieOrSeriesModel.getMovieRating() != null) {
            MovieRatingView ratingView = (MovieRatingView) findViewById(R.id.detailed_movie_rating);
            ratingView.setPGRatingForAudienceAndAdvertising(movieOrSeriesModel.getMovieRating());
        }


        // TODO Load the poster using a BindingAdapter with multiple parameter : one for the URL, one for the default image in case of error
        ImageView posterView = (ImageView) findViewById(R.id.simple_movie_poster);
        if(movieOrSeriesModel.getPosterModel().getPosterUrl() != null){
            Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(movieOrSeriesModel.getPosterModel());
            posterView.setImageDrawable(drawableFromPoster);
        } else {
            posterView.setImageResource(R.drawable.ic_empty_poster);
        }
    }

    private void setVisible(View view, boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void setFont(TextView view, String newFont){
        if(lastFont == null || !lastFont.equals(newFont)) {
            lastFont = newFont;
            Typeface type = Typeface.createFromAsset(getAssets(), "fonts/helvetica_normal.ttf");
            view.setTypeface(type);
        }
    }

    private void setStarColor(RatingBar ratingBar, @ColorRes int starColor){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), starColor, null), PorterDuff.Mode.SRC_ATOP);
    }
}
