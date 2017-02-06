package fr.soat.demo.exo2_viewmodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private MovieSeriesBusinessService movieSeriesBusinessService;

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
    private ImageView detailedMovieMetacriticIcon;
    private RatingBar detailedMovieMetacriticRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loading of the movie model
        movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        MovieSeriesModel movieModel = movieSeriesBusinessService.getMovieSeriesModelFromName("Pulp Fiction");

        // Loading of the poster picture
        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(movieModel.getPosterModel());
        movieModel.getPosterModel().setImageLoaded(drawableFromPoster);


        // TODO Uncomment when ViewModel is ready, and comment the other init method
//        initWithDataBindingMethod(movieModel);
        initWithTraditionnalMethod(movieModel);
    }

    private void initWithTraditionnalMethod(MovieSeriesModel movieModel){
        setContentView(R.layout.view_detailed_movie);

        initFields();

        initData(movieModel);
    }

    private void initWithDataBindingMethod(MovieSeriesModel movieModel) {
        ViewDetailedMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.view_detailed_movie);

        DetailledMovieViewModel detailledMovieViewModel = new DetailledMovieViewModel(movieModel);

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
        detailedMovieMetacriticIcon = (ImageView) findViewById(R.id.detailed_movie_metacritic_rating_icon);
        detailedMovieMetacriticRating = (RatingBar) findViewById(R.id.detailed_movie_metacritic_rating);
    }

    private void initData(MovieSeriesModel movieModel) {
        initPosterData(movieModel.getPosterModel());

        initDetailedInfo(movieModel);
    }

    private void initPosterData(PosterModel posterModel) {
        movieTitle.setText(posterModel.getTitle());

        String dateString = DateUtils.formatDateToString(posterModel.getReleaseDate(), getString(R.string.date_format_release));
        movieYear.setText(dateString);

        String genres = StringUtils.assembleString(posterModel.getGenres(), ", ");
        movieGenres.setText(genres);

        String actorDescription = getString(R.string.format_actors, StringUtils.assembleString(posterModel.getActors(), ", "));
        movieActors.setText(actorDescription);

        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
        if (drawableFromPoster != null) {
            moviePoster.setImageDrawable(drawableFromPoster);
        }
    }

    private void initDetailedInfo(final MovieSeriesModel movieModel) {
        detailedMoviePlot.setText(movieModel.getPlot());

        if(movieModel.getDirector() != null){
            detailedMovieDirector.setText(getString(R.string.format_director, movieModel.getDirector()));
        } else {
            detailedMovieDirector.setVisibility(GONE);
        }

        // Writers
        String writers = StringUtils.assembleString(movieModel.getWriters(), ", ");
        if (writers != null) {
            detailedMovieWriters.setText(getString(R.string.format_writers, writers));
        } else {
            detailedMovieWriters.setVisibility(GONE);
        }

        // Country
        String mainCountry = movieModel.getCountry() != null ? movieModel.getCountry().get(0) : null;
        int countryDrawable = DrawableUtils.getCountryDrawable(mainCountry);
        if (countryDrawable > 0) {
            detailedMovieCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, countryDrawable, 0);
            detailedMovieCountry.setText(R.string.format_country_with_drawable);
        } else {
            detailedMovieCountry.setText(getString(R.string.format_country_with_param, mainCountry));
        }

        // Runtime
        int runtime = movieModel.getRuntime();
        if (runtime > 0) {
            String durationString = DateUtils.formatDuration(this, runtime);
            detailedMovieDuration.setText(getString(R.string.format_duration, durationString));
        } else {
            detailedMovieDuration.setVisibility(GONE);
        }

        if(movieModel.getImdbRating() >= 0) {
            detailedMovieImdbIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // This code show a popup below the view with the number of voting
                    PopupUtils.showPopupForVotes(MainActivity.this, view, movieModel.getImdbVotes());
                }
            });
            float starRating = movieModel.getImdbRating() / 2.0f;
            detailedMovieImdbRating.setRating(starRating);
            LayerDrawable stars = (LayerDrawable) detailedMovieImdbRating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        } else {
            detailedMovieImdbRating.setVisibility(GONE);
            detailedMovieImdbIcon.setVisibility(GONE);
        }

        if(movieModel.getImdbRating() >= 0) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // This code redirect to the web main page of Metacritic
                    String url = getString(R.string.metacritic_web_adress);
                    final Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(webIntent);
                }
            };
            detailedMovieMetacriticIcon.setOnClickListener(onClickListener);
            detailedMovieMetacriticRating.setOnClickListener(onClickListener);

            float starRating = movieModel.getMetascore() / 20.0f;
            detailedMovieMetacriticRating.setRating(starRating);
            LayerDrawable stars = (LayerDrawable) detailedMovieMetacriticRating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        } else {
            detailedMovieMetacriticRating.setVisibility(GONE);
            detailedMovieMetacriticIcon.setVisibility(GONE);
        }


    }
}
