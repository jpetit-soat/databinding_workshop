package fr.soat.demo.exo2_viewmodel;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.soat.demo.exo2_viewmodel.databinding.ViewDetailedMovieBinding;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;
import fr.soat.demo.moviesmodel.utils.DateUtils;
import fr.soat.demo.moviesmodel.utils.DrawableUtils;
import fr.soat.demo.moviesmodel.utils.PopupUtils;
import fr.soat.demo.moviesmodel.utils.StringUtils;

public class MainActivity extends AppCompatActivity {

    // TODO Pass this boolean to "true" when ViewModel and binding is ready
    public static final boolean USING_DATABINDING = true;

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieActors;
    private TextView movieGenres;

    private TextView detailedMoviePlot;
    private TextView detailedMovieDirector;
    private TextView detailedMovieWriters;
    private TextView detailedMovieCountry;
    private TextView detailedMovieDuration;
    private ImageView detailedMovieImdbIcon;
    private RatingBar detailedMovieImdbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loading of the movie model
        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        MovieSeriesModel movieModel = movieSeriesBusinessService.getMovieSeriesModelFromName("Pulp Fiction");

        // Loading of the poster picture
        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(movieModel.getPosterModel());
        movieModel.getPosterModel().setImageLoaded(drawableFromPoster);

        if(USING_DATABINDING){
            initWithDataBindingMethod(movieModel);
        } else {
            initWithTraditionnalMethod(movieModel);
        }
    }

    private void initWithTraditionnalMethod(MovieSeriesModel movieModel){
        setContentView(R.layout.view_detailed_movie);

        initFields();

        initData(movieModel);
    }

    private void initWithDataBindingMethod(MovieSeriesModel movieModel) {
        ViewDetailedMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.view_detailed_movie);

        DetailledMovieViewModel detailledMovieViewModel = new DetailledMovieViewModel(this, movieModel);

        binding.setModel(detailledMovieViewModel);
    }

    private void initFields() {
        // Poster view
        moviePoster = (ImageView) findViewById(R.id.simple_movie_poster);
        movieTitle = (TextView) findViewById(R.id.simple_movie_title);
        movieYear = (TextView) findViewById(R.id.simple_movie_year);
        movieActors = (TextView) findViewById(R.id.simple_movie_actors);
        movieGenres = (TextView) findViewById(R.id.simple_movie_genre);

        detailedMoviePlot = (TextView) findViewById(R.id.detailed_movie_plot);
        detailedMovieDirector = (TextView) findViewById(R.id.detailed_movie_director);
        detailedMovieWriters = (TextView) findViewById(R.id.detailed_movie_writers);
        detailedMovieCountry = (TextView) findViewById(R.id.detailed_movie_country);
        detailedMovieDuration = (TextView) findViewById(R.id.detailed_movie_duration);
        detailedMovieImdbIcon = (ImageView) findViewById(R.id.detailed_movie_imdb_icon);
        detailedMovieImdbRating = (RatingBar) findViewById(R.id.detailed_movie_imdb_rating);
    }

    private void initData(MovieSeriesModel movieModel) {
        initPosterData(movieModel.getPosterModel());

        initDetailedInfo(movieModel);
    }

    private void initPosterData(PosterModel posterModel) {
        // Theses methods aren't usefull anymore with DataBinding : we can reuse
        // the PosterModel with the binded layout using the <include> tag

        if(posterModel.getImageLoaded() != null){
            moviePoster.setImageDrawable(posterModel.getImageLoaded());
        }

        movieTitle.setText(posterModel.getTitle());

        movieYear.setText(posterModel.getFormattedDate(this));

        movieGenres.setText(posterModel.getFormattedGenres());

        movieActors.setText(posterModel.getFormattedActors(this));
    }

    private void initDetailedInfo(final MovieSeriesModel movieModel) {

        // First we format the data coming from the MovieSeriesModel

        String plot;
        String director = null;
        String writers = null;
        @DrawableRes int countryDrawableRes;
        String countryText;
        String duration = null;
        float rating = -1;

        // Plot
        plot = movieModel.getPlot();

        // Director
        if(movieModel.getDirector() != null){
            director = getString(R.string.format_director, movieModel.getDirector());
        }

        // Writers
        if (movieModel.getWriters() != null && !movieModel.getWriters().isEmpty()) {
            writers = getString(R.string.format_writers, StringUtils.assembleString(movieModel.getWriters(), ", "));
        }

        // Country
        String mainCountry = movieModel.getCountry() != null ? movieModel.getCountry().get(0) : null;
        countryDrawableRes = DrawableUtils.getCountryDrawable(mainCountry);
        if (countryDrawableRes > 0) {
            countryText = getString(R.string.format_country_with_drawable);
        } else {
            countryText = getString(R.string.format_country_with_param, mainCountry);
        }

        // Runtime
        int runtimeValue = movieModel.getRuntime();
        if (runtimeValue > 0) {
            String durationString = DateUtils.formatDuration(this, runtimeValue);
            duration = getString(R.string.format_duration, durationString);
        }

        // Rating
        if(movieModel.getImdbRating() >= 0) {
            rating = movieModel.getImdbRating() / 2.0f;
        }


        // Then we put theses formatted data into their respectives view

        detailedMoviePlot.setText(plot);
        detailedMovieDirector.setText(director);
        detailedMovieWriters.setText(writers);
        detailedMovieCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, countryDrawableRes, 0);
        detailedMovieCountry.setText(countryText);
        detailedMovieDuration.setText(getString(R.string.format_duration, duration));
        detailedMovieImdbRating.setRating(rating);
        detailedMovieImdbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This code show a popup below the view with the number of voting
                PopupUtils.showPopupForVotes(MainActivity.this, view, movieModel.getImdbVotes());
            }
        });
    }
}
