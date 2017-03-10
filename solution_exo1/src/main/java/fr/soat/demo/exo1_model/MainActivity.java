package fr.soat.demo.exo1_model;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.soat.demo.exo1_model.databinding.ViewPosterBinding;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.PosterModel;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_NAME = "Guardians of the galaxy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       ViewPosterBinding binding = DataBindingUtil.setContentView(this, R.layout.view_poster);

        // This object contains all the needed data for the view
        PosterModel posterModel = getPosterModel();

        binding.setModel(posterModel);
    }

    public PosterModel getPosterModel() {
        // This code retrieve the wanted movie model and gives him a Drawable for the poster
        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(this);
        PosterModel posterModel = movieSeriesBusinessService.getPosterModelFromName(MOVIE_NAME);
        Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
        posterModel.setImageLoaded(drawableFromPoster);
        return posterModel;
    }
}
