package fr.soat.demo.exo5_simplified_recyclerview.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo5_simplified_recyclerview.BR;
import fr.soat.demo.exo5_simplified_recyclerview.FilterListener;
import fr.soat.demo.exo5_simplified_recyclerview.R;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.business.filters.RatingFilter;
import fr.soat.demo.moviesmodel.business.filters.SearchFilter;
import fr.soat.demo.moviesmodel.business.filters.TypeFilter;
import fr.soat.demo.moviesmodel.model.CulturalType;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;


/**
 * Created by yann_huriez on 07/02/17.
 */

public class FiltersViewModel extends BaseObservable {

    private Context context;
    private FilterListener listener;
    private MovieSeriesBusinessService movieSeriesBusinessService;

    private String searchString = null;
    private CulturalType selectedType = null;
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
        notifyPropertyChanged(BR.resultCount);
    }

    public int getSelectedType() {
        return convertCulturalTypeToButtonId(selectedType);
    }

    public void setSelectedType(int buttonId) {
        this.selectedType = convertButtonIdToCulturalType(buttonId);
        notifyPropertyChanged(BR.resultCount);
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
        notifyPropertyChanged(BR.rating);
        notifyPropertyChanged(BR.resultCount);
    }

    @Bindable
    public String getResultCount() {
        List<AbstractMovieSeriesFilter> filters = new ArrayList<>();

        // Search
        if(!TextUtils.isEmpty(searchString)){
            filters.add(new SearchFilter(searchString));
        }

        // Type
        if(selectedType != null){
            filters.add(new TypeFilter(selectedType));
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

    private int convertCulturalTypeToButtonId(CulturalType type){
        if(type != null) {
            switch (type) {
                case MOVIE:
                    return R.id.view_filter_movie_button;
                case SERIES:
                    return R.id.view_filter_series_button;
            }
        }
        return R.id.view_filter_all_button;
    }

    private CulturalType convertButtonIdToCulturalType(int buttonId){
        switch (buttonId){
            case R.id.view_filter_all_button:
                return null;
            case R.id.view_filter_movie_button:
                return CulturalType.MOVIE;
            case R.id.view_filter_series_button:
                return CulturalType.SERIES;
        }
        return null;
    }
}
