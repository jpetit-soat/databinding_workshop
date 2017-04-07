package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fr.soat.demo.exo5_simplified_recyclerview.databinding.ViewDetailedMovieBinding;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.DetailedMovieViewModel;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class DetailedMovieViewHolder extends RecyclerView.ViewHolder {

    private final ViewDetailedMovieBinding binding;
    private final Context context;

    public DetailedMovieViewHolder(View itemView) {
        super(itemView);
        binding = ViewDetailedMovieBinding.bind(itemView);
        context = itemView.getContext();
    }

    public void setMovieSeriesModel(MovieSeriesModel movieSeriesModel) {
        DetailedMovieViewModel detailedMovieViewModel = new DetailedMovieViewModel(context, movieSeriesModel);
        binding.setModel(detailedMovieViewModel);

        binding.executePendingBindings();
    }
}
