package fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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

        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, viewType_layoutRes, parent, false);
        return new RecyclerBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerBindingViewHolder holder, int position) {
        holder.setViewModel(viewModels.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return viewModels.get(position).getLayoutRes();
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
