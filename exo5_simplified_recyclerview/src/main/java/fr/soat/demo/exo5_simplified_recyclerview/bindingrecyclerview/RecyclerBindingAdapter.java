package fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yann_huriez on 24/08/16.
 */
public class RecyclerBindingAdapter extends RecyclerView.Adapter<RecyclerBindingViewHolder> {

    private final List<BindableViewModel> viewModels = new ArrayList<>();

    private LayoutInflater mInflater;

    @Override
    public RecyclerBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType_layoutRes) {
        if(mInflater == null){
            mInflater = LayoutInflater.from(parent.getContext());
        }

        // TODO Inflate the right binding and return the generic ViewHolder with the binding class

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerBindingViewHolder holder, int position) {
        // TODO Give to the view holder the right ViewModel
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Return the layout res id here, in order to re-employ it for inflation in the onCreateViewHolder method
        return -1;
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }


    public void setViewModels(List<BindableViewModel> models) {
        viewModels.clear();
        viewModels.addAll(models);

        notifyDataSetChanged();
    }
}
