package fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by yann_huriez on 24/08/16.
 */

public class RecyclerBindingViewHolder extends RecyclerView.ViewHolder {

    public RecyclerBindingViewHolder(ViewDataBinding viewBinding) {
        super(viewBinding.getRoot());
        // TODO Save the binding
    }

    public void setViewModel(BindableViewModel viewModel) {
        // TODO set the ViewModel to the saved binding

        // TODO Ask the binding class to immediately execute the binding operation
    }
}
