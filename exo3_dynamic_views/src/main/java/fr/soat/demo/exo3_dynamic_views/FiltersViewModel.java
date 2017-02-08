package fr.soat.demo.exo3_dynamic_views;

import android.content.Context;
import android.databinding.BaseObservable;

import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;

/**
 * Created by yann_huriez on 07/02/17.
 */

public class FiltersViewModel extends BaseObservable {

    private Context context;
    private MovieSeriesBusinessService movieSeriesBusinessService;

    public FiltersViewModel(Context context) {
        this.context = context;
        movieSeriesBusinessService = new MovieSeriesBusinessService(context);
    }

    // TODO Create methods here to allow the layout to manipulate data

}
