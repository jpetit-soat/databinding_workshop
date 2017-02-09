package fr.soat.demo.exo4_bindingadapters;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.soat.demo.exo4_bindingadapters.databinding.ViewFilterAndDetailledMovieBinding;
import fr.soat.demo.exo4_bindingadapters.viewmodel.DetailledMovieViewModel;
import fr.soat.demo.exo4_bindingadapters.viewmodel.FiltersViewModel;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

public class MainActivity extends AppCompatActivity {

    private MovieSeriesBusinessService movieSeriesBusinessService;
    private ViewFilterAndDetailledMovieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.view_filter_and_detailled_movie);

        // Loading of the movie model
        movieSeriesBusinessService = new MovieSeriesBusinessService(this);

        binding.setFilterModel(new FiltersViewModel(this));


//        LayerDrawable stars = (LayerDrawable) filterRating.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }


    public void showDetailledMovieOrSeries(MovieSeriesModel movieOrSeriesModel){
        DetailledMovieViewModel detailledMovieViewModel = new DetailledMovieViewModel(this, movieOrSeriesModel);

        binding.setMovieModel(detailledMovieViewModel);

        // TODO The code contained in this method can entirely be replace with BindingAdapters
        updateViewWithDetails(movieOrSeriesModel);
    }


    private void updateViewWithDetails(MovieSeriesModel movieOrSeriesModel) {

    }
}
