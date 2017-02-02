package fr.soat.demo.exo1_helloworld;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import fr.soat.demo.moviesmodel.DateUtils;
import fr.soat.demo.moviesmodel.StringUtils;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.PosterModel;

public class MainActivity extends AppCompatActivity {

    private MovieSeriesBusinessService movieSeriesBusinessService;

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieActors;
    private TextView movieGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_poster);

        initFields();

//        new DownloadPosterASyncTask(this).execute();

        movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        PosterModel posterModel = movieSeriesBusinessService.getPosterModelFromName("Guardians of the galaxy");
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
        movieTitle.setText(posterModel.getTitle());

        String dateString = DateUtils.formatDateToString(posterModel.getReleaseDate(), getString(R.string.date_format_release));
        movieYear.setText(dateString);

        String genres = StringUtils.assembleString(posterModel.getGenres(), ", ");
        movieGenres.setText(genres);

        String actorDescription = getString(R.string.simple_movie_actor_description, StringUtils.assembleString(posterModel.getActors(), ", "));
        movieActors.setText(actorDescription);

        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
        if(drawableFromPoster != null){
            moviePoster.setImageDrawable(drawableFromPoster);
        }
    }
}
