package fr.soat.demo.exo4_bindingadapters.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo4_bindingadapters.FilterListener;
import fr.soat.demo.exo4_bindingadapters.R;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.business.filters.RatingFilter;
import fr.soat.demo.moviesmodel.business.filters.SearchFilter;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 07/02/17.
 */

public class FiltersViewModel extends BaseObservable {

    private Context context;
    private FilterListener listener;
    private MovieSeriesBusinessService movieSeriesBusinessService;

    private String searchString = null;
    private int ratingProgress = 0;

    private final List<MovieSeriesModel> filteredMovieSeries = new ArrayList<>();

    public FiltersViewModel(Context context, FilterListener listener) {
        this.context = context;
        this.listener = listener;
        movieSeriesBusinessService = new MovieSeriesBusinessService(context);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        notifyPropertyChanged(fr.soat.demo.exo4_bindingadapters.BR.resultCount);
    }

    @Bindable
    public float getRating() {
        return (float) ratingProgress / 2.0f;
    }

    public int getRatingProgress() {
        return ratingProgress;
    }

    public void setRatingProgress(int ratingProgress) {
        this.ratingProgress = ratingProgress;
        notifyPropertyChanged(fr.soat.demo.exo4_bindingadapters.BR.rating);
        notifyPropertyChanged(fr.soat.demo.exo4_bindingadapters.BR.resultCount);
    }

    @Bindable
    public String getResultCount() {
        List<AbstractMovieSeriesFilter> filters = new ArrayList<>();

        // Search
        if(!TextUtils.isEmpty(searchString)){
            filters.add(new SearchFilter(searchString));
        }

        // Rating
        if(ratingProgress >= 0){
            filters.add(new RatingFilter(ratingProgress));
        }

        filteredMovieSeries.clear();
        filteredMovieSeries.addAll(movieSeriesBusinessService.getFilteredMovieSeries(filters));

        String resultCount;
        if(filteredMovieSeries.isEmpty()){
            resultCount = context.getString(R.string.search_button_no_result);
        } else {
            resultCount = context.getResources().getQuantityString(R.plurals.search_button_result, filteredMovieSeries.size(), filteredMovieSeries.size());
        }

        return resultCount;
    }

    public void onResultButtonClicked(){
        if(listener != null && !filteredMovieSeries.isEmpty()){
            listener.onShowMovieSeries(filteredMovieSeries);
        }
    }
}
