package fr.soat.demo.exo4_bindingadapters.utils;

import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.soat.demo.exo4_bindingadapters.R;
import fr.soat.demo.moviesmodel.model.MovieRating;

/**
 * Created by yann_huriez on 09/02/17.
 */
@BindingMethods({
        @BindingMethod(type = MovieRatingView.class, attribute = "android:rating", method = "setRatingForAudienceAndAdvertising")
})
public class MovieRatingView extends LinearLayout {

    private ImageView ratingImageView;
    private TextView ratingTextView;

    public MovieRatingView(Context context) {
        super(context);
        init();
    }

    public MovieRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View mainView = inflate(getContext(), R.layout.view_movie_rating, this);

        ratingImageView = (ImageView) mainView.findViewById(R.id.pg_rating_image);
        ratingTextView = (TextView) mainView.findViewById(R.id.pg_rating_advertising);
    }

    public void setRatingForAudienceAndAdvertising(MovieRating rating){
        @ColorRes int ratingColor = -1;
        @StringRes int ratingText = -1;
        @DrawableRes int ratingImage = -1;

        if(rating != null) {
            switch (rating) {
                case G:
                    ratingColor = R.color.rated_green;
                    ratingText = R.string.PG_rating_advertising_green;
                    ratingImage = R.drawable.ic_rated_g;
                    break;
                case PG:
                    ratingColor = R.color.rated_green;
                    ratingText = R.string.PG_rating_advertising_green;
                    ratingImage = R.drawable.ic_rated_pg;
                    break;
                case PG_13:
                    ratingColor = R.color.rated_yellow;
                    ratingText = R.string.PG_rating_advertising_yellow;
                    ratingImage = R.drawable.ic_rated_pg_13;
                    break;
                case R:
                    ratingColor = R.color.rated_red;
                    ratingText = R.string.PG_rating_advertising_red;
                    ratingImage = R.drawable.ic_rated_r;
                    break;
                case NC_17:
                    ratingColor = R.color.rated_red;
                    ratingText = R.string.PG_rating_advertising_red;
                    ratingImage = R.drawable.ic_rated_nc_17;
                    break;
            }
        }
        if(ratingImage >= 0){
            ratingImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), ratingImage, null));
            ratingTextView.setTextColor(ResourcesCompat.getColor(getResources(), ratingColor, null));
            ratingTextView.setText(ratingText);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }
}
