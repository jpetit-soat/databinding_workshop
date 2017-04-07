package fr.soat.demo.exo5_simplified_recyclerview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo5_simplified_recyclerview.R;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class MovieSeriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MovieSeriesItem> mItems = new ArrayList<>();
    private LayoutInflater mInflater;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        View inflatedView;
        switch (viewType) {
            case MovieSeriesItem.TYPE_FILTER:
                inflatedView = mInflater.inflate(R.layout.view_filters, parent, false);
                return new FilterViewHolder(inflatedView);

            case MovieSeriesItem.TYPE_TTILE:
                inflatedView = mInflater.inflate(R.layout.view_title, parent, false);
                return new TitleViewHolder(inflatedView);

            case MovieSeriesItem.TYPE_DETAILED_MOVIE:
                inflatedView = mInflater.inflate(R.layout.view_detailed_movie, parent, false);
                return new DetailedMovieViewHolder(inflatedView);

            case MovieSeriesItem.TYPE_POSTER:
                inflatedView = mInflater.inflate(R.layout.view_card_poster, parent, false);
                return new PosterViewHolder(inflatedView);
        }
        throw new UnsupportedOperationException("Unknown view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieSeriesItem movieSeriesItem = mItems.get(position);
        switch (getItemViewType(position)) {
            case MovieSeriesItem.TYPE_TTILE:
                ((TitleViewHolder)holder).setTitle(movieSeriesItem.getTitle());
                break;

            case MovieSeriesItem.TYPE_DETAILED_MOVIE:
                ((DetailedMovieViewHolder)holder).setMovieSeriesModel(movieSeriesItem.getMovieSeriesModel());
                break;

            case MovieSeriesItem.TYPE_POSTER:
                ((PosterViewHolder)holder).setPosterModel(movieSeriesItem.getPosterModel());
                break;

            case MovieSeriesItem.TYPE_FILTER:
                ((FilterViewHolder)holder).setListener(movieSeriesItem.getFilterListener());
                // TODO Bonus : Once the RecyclerView with binding works, try to save the last filter configuration and input it to the FilterViewModel
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getItemType();
    }

    public void setItems(List<MovieSeriesItem> items){
        mItems.clear();
        mItems.addAll(items);

        notifyDataSetChanged();
    }
}
