package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import fr.soat.demo.exo5_simplified_recyclerview.databinding.ViewCardPosterBinding;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.PosterViewModel;
import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class PosterViewHolder extends RecyclerView.ViewHolder {

    private final ViewCardPosterBinding binding;

    public PosterViewHolder(View itemView) {
        super(itemView);
        binding = ViewCardPosterBinding.bind(itemView);
    }

    public void setPosterModel(PosterModel posterModel) {
        PosterViewModel posterViewModel = new PosterViewModel(posterModel);
        binding.setModel(posterViewModel);

        binding.executePendingBindings();
    }
}
