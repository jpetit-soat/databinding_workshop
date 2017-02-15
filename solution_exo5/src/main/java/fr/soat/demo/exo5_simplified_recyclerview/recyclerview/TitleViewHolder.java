package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import fr.soat.demo.exo5_simplified_recyclerview.databinding.ViewTitleBinding;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.TitleViewModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {

    private final ViewTitleBinding binding;

    public TitleViewHolder(View itemView) {
        super(itemView);
        binding = ViewTitleBinding.bind(itemView);
    }

    public void setTitle(String title) {
        TitleViewModel titleViewModel = new TitleViewModel(title);
        binding.setModel(titleViewModel);

        binding.executePendingBindings();
    }
}
