package fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by yann_huriez on 24/08/16.
 */

public class RecyclerBindingViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public RecyclerBindingViewHolder(ViewDataBinding viewBinding) {
        super(viewBinding.getRoot());
        binding = viewBinding;
    }

    public void setViewModel(BindableViewModel viewModel) {
        binding.setVariable(viewModel.getVariableId(), viewModel);
        binding.executePendingBindings();
    }
}
