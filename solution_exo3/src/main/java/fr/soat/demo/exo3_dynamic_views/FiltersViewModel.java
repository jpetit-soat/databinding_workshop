package fr.soat.demo.exo3_dynamic_views;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.business.filters.RatingFilter;
import fr.soat.demo.moviesmodel.business.filters.SearchFilter;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.utils.PopupUtils;

/**
 * Created by yann_huriez on 07/02/17.
 */

public class FiltersViewModel extends BaseObservable {

    private Context context;
    private MovieSeriesBusinessService movieSeriesBusinessService;

    private String searchString = null;
    private int ratingProgress = 0;
    private int totalResult = 0;

    public FiltersViewModel(Context context) {
        this.context = context;
        movieSeriesBusinessService = new MovieSeriesBusinessService(context);
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        notifyPropertyChanged(fr.soat.demo.exo3_dynamic_views.BR.resultCount);
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
        notifyPropertyChanged(fr.soat.demo.exo3_dynamic_views.BR.rating);
        notifyPropertyChanged(fr.soat.demo.exo3_dynamic_views.BR.resultCount);
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

        List<MovieSeriesModel> filteredMovieSeries = movieSeriesBusinessService.getFilteredMovieSeries(filters);
        totalResult = filteredMovieSeries.size();

        String resultCount;
        if(totalResult == 0){
            resultCount = context.getString(R.string.search_button_no_result);
        } else {
            resultCount = context.getResources().getQuantityString(R.plurals.search_button_result, totalResult, totalResult);
        }

        return resultCount;
    }

    public void onResultButtonClicked(View view){
        PopupUtils.showPopupForItemCount(context, view, totalResult);
    }
}
