package fr.soat.demo.exo2_viewmodel;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import fr.soat.demo.exo2_viewmodel.databinding.ViewDetailedMovieBinding;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.model.PosterModel;
import fr.soat.demo.moviesmodel.utils.DateUtils;
import fr.soat.demo.moviesmodel.utils.DrawableUtils;

public class MainActivity extends AppCompatActivity {

    // TODO Pass this boolean to "true" when ViewModel and binding are ready
    public static final boolean USING_DATABINDING = true;

    public static final String MOVIE_NAME = "The Dark Knight";

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieActors;
    private TextView movieGenres;

    private TextView detailedMoviePlot;
    private TextView detailedMovieDirector;
    private TextView detailedMovieCountry;
    private TextView detailedMovieDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loading of the movie model
        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        MovieSeriesModel movieModel = movieSeriesBusinessService.getMovieSeriesModelFromName(MOVIE_NAME);

        // Loading of the poster picture
        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(movieModel.getPosterModel());
        movieModel.getPosterModel().setImageLoaded(drawableFromPoster);

        if(!USING_DATABINDING){
            initWithTraditionnalMethod(movieModel);
        } else {
            initWithDataBindingMethod(movieModel);
        }
    }

    private void initWithTraditionnalMethod(MovieSeriesModel movieModel){
        setContentView(R.layout.view_detailed_movie);

        initFields();

        initData(movieModel);
    }

    private void initWithDataBindingMethod(MovieSeriesModel movieModel) {
        ViewDetailedMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.view_detailed_movie);

        DetailedMovieViewModel detailedMovieViewModel = new DetailedMovieViewModel(this, movieModel);

        binding.setModel(detailedMovieViewModel);
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
        detailedMovieCountry = (TextView) findViewById(R.id.detailed_movie_country);
        detailedMovieDuration = (TextView) findViewById(R.id.detailed_movie_duration);
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
        @DrawableRes int countryDrawableRes;
        String duration = null;

        // Plot
        plot = movieModel.getPlot();

        // Director
        if(movieModel.getDirector() != null){
            director = getString(R.string.format_director, movieModel.getDirector());
        }

        // Runtime
        int runtimeValue = movieModel.getRuntime();
        if (runtimeValue > 0) {
            String durationString = DateUtils.formatDuration(this, runtimeValue);
            duration = getString(R.string.format_duration, durationString);
        }

        // Country
        String mainCountry = movieModel.getCountry().get(0);
        countryDrawableRes = DrawableUtils.getCountryDrawable(mainCountry);

        // Then we put theses formatted data into their respectives view

        detailedMoviePlot.setText(plot);
        detailedMovieDirector.setText(director);
        detailedMovieDuration.setText(getString(R.string.format_duration, duration));
        detailedMovieCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, countryDrawableRes, 0);
    }
}
