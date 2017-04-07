package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import fr.soat.demo.exo5_simplified_recyclerview.FilterListener;
import fr.soat.demo.exo5_simplified_recyclerview.databinding.ViewFiltersBinding;
import fr.soat.demo.exo5_simplified_recyclerview.viewmodel.FiltersViewModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class FilterViewHolder extends RecyclerView.ViewHolder {

    private final ViewFiltersBinding binding;
    private final Context context;

    public FilterViewHolder(View itemView) {
        super(itemView);
        binding = ViewFiltersBinding.bind(itemView);
        context = itemView.getContext();
    }

    public void setListener(FilterListener listener){
        FiltersViewModel filtersViewModel = new FiltersViewModel(context, listener);
        binding.setModel(filtersViewModel);

        binding.executePendingBindings();
    }
}
