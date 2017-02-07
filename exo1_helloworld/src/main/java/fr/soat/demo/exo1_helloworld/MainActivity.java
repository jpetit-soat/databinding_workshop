package fr.soat.demo.exo1_helloworld;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.PosterModel;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_NAME = "Guardians of the galaxy";

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieActors;
    private TextView movieGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Replace with right method coming from DataBindingUtils
        setContentView(R.layout.view_poster);

        // TODO This method is not used with DataBinding
        initFields();

        // This code retrieve the wanted movie model and gives him a Drawable for the poster
        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        PosterModel posterModel = movieSeriesBusinessService.getPosterModelFromName(MOVIE_NAME);
        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
        posterModel.setImageLoaded(drawableFromPoster);

        // TODO The data loading must be made by the layout XML
        initData(posterModel);
    }

    private void initFields() {
        moviePoster = (ImageView) findViewById(R.id.simple_movie_poster);
        movieTitle = (TextView) findViewById(R.id.simple_movie_title);
        movieYear = (TextView) findViewById(R.id.simple_movie_year);
        movieActors = (TextView) findViewById(R.id.simple_movie_actors);
        movieGenres = (TextView) findViewById(R.id.simple_movie_genre);
    }

    private void initData(PosterModel posterModel) {
        if(posterModel.getImageLoaded() != null){
            moviePoster.setImageDrawable(posterModel.getImageLoaded());
        }

        movieTitle.setText(posterModel.getTitle());

        movieYear.setText(posterModel.getFormattedDate(this));

        movieGenres.setText(posterModel.getFormattedGenres());

        movieActors.setText(posterModel.getFormattedActors(this));

        // TODO Bonus
        // If you feel great, try to make a binding
        // without using the methods "getFormatted..."
    }
}
