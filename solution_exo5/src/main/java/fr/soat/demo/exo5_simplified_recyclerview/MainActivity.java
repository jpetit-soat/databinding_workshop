package fr.soat.demo.exo5_simplified_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview.BindableViewModel;
import fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview.RecyclerBindingAdapter;
import fr.soat.demo.exo5_simplified_recyclerview.recyclerview.MovieSeriesAdapter;
import fr.soat.demo.exo5_simplified_recyclerview.recyclerview.MovieSeriesItem;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.DetailedMovieViewModel;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.FiltersViewModel;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.PosterViewModel;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.TitleViewModel;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

public class MainActivity extends AppCompatActivity implements FilterListener {

    public static final boolean USING_DATABINDING = true;

    private MovieSeriesAdapter adapter;
    private RecyclerBindingAdapter bindingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recyclerview);

        if (!USING_DATABINDING) {
            initRecyclerView();
        } else {
            initRecyclerBindingView();
        }
    }

    @Override
    public void onShowMovieSeries(List<MovieSeriesModel> listModel) {
        if(!USING_DATABINDING){
            // Here for traditional we generate a list of MovieSeriesItem with the wanted elements to show
            // then we pass them to the adapter, that will display them in a list
            List<MovieSeriesItem> items = createMovieSeriesItems(listModel);
            adapter.setItems(items);

        } else {
            // For DataBinding version, we generate a list of BindableViewModel that we pass to the generic
            // RecyclerBindingAdapter, configured to accept every implementation of BindableViewModel
            List<BindableViewModel> movieSeriesViewModels = createMovieSeriesViewModels(listModel);
            bindingAdapter.setViewModels(movieSeriesViewModels);

            // TODO Bonus : there is a way to simplify again : you can avoid getting RecyclerView reference and declaring adapter using a BindingAdapter and a ViewModel containing a list of BindableViewModel
        }
    }

    // ///////////////////
    // Traditional method
    // ///////////////////

    private void initRecyclerView() {
        // RecyclerView configuration
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieSeriesAdapter();
        recyclerView.setAdapter(adapter);

        onShowMovieSeries(null);
    }

    private List<MovieSeriesItem> createMovieSeriesItems(List<MovieSeriesModel> listMovieSeries) {
        List<MovieSeriesItem> result = new ArrayList<>();

        // First item is always the filter item
        result.add(MovieSeriesItem.createFilterItem(this));

        if (listMovieSeries != null && !listMovieSeries.isEmpty()) {
            // Then, if we have results to show, we first add a title for the first (and best) proposal
            result.add(MovieSeriesItem.createTitleItem(getString(R.string.list_title_best_proposal)));

            // We add the first result as a detailed movie item
            result.add(MovieSeriesItem.createDetailedMovieItem(listMovieSeries.get(0)));

            // If other results, we show a title presenting them
            if (listMovieSeries.size() > 1) {
                result.add(MovieSeriesItem.createTitleItem(getString(R.string.list_title_other_proposals)));

                for (MovieSeriesModel movieOrSeries : listMovieSeries.subList(1, listMovieSeries.size())) {
                    // At last we add all the other results as poster model
                    result.add(MovieSeriesItem.createPosterItem(movieOrSeries.getPosterModel()));
                }
            }
        }

        return result;
    }

    // //////////////////
    // DataBinding method
    // //////////////////

    private void initRecyclerBindingView() {
        // RecyclerView configuration
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bindingAdapter = new RecyclerBindingAdapter();
        recyclerView.setAdapter(bindingAdapter);

        onShowMovieSeries(null);
    }

    private List<BindableViewModel> createMovieSeriesViewModels(List<MovieSeriesModel> listMovieSeries){
        List<BindableViewModel> result = new ArrayList<>();


        // First item is always the filter item
        result.add(new FiltersViewModel(this, this));

        if (listMovieSeries != null && !listMovieSeries.isEmpty()) {
            // Then, if we have results to show, we first add a title for the first (and best) proposal
            result.add(new TitleViewModel(getString(R.string.list_title_best_proposal)));

            // We add the first result as a detailed movie item
            result.add(new DetailedMovieViewModel(this, listMovieSeries.get(0)));

            // If other results, we show a title presenting them
            if (listMovieSeries.size() > 1) {
                result.add(new TitleViewModel(getString(R.string.list_title_other_proposals)));

                for (MovieSeriesModel movieOrSeries : listMovieSeries.subList(1, listMovieSeries.size())) {
                    // At last we add all the other results as poster model
                    result.add(new PosterViewModel(movieOrSeries.getPosterModel()));
                }
            }
        }

        return result;
    }
}
