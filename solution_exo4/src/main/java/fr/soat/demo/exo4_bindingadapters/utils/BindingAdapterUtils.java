package fr.soat.demo.exo4_bindingadapters.utils;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 09/02/17.
 */

public class BindingAdapterUtils {

    @BindingAdapter("setVisible")
    public static void setVisible(View view, boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("customFont")
    public static void setFont(TextView view, String lastFont, String newFont){
        if(lastFont == null && newFont == null){
            return;
        }

        if(lastFont == null || !lastFont.equals(newFont)) {
            lastFont = newFont;
            Typeface type = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/" + lastFont + ".ttf");
            view.setTypeface(type);
        }
    }

    @BindingAdapter("starColor")
    public static void setStarColor(RatingBar ratingBar, @ColorInt int starColor){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(starColor, PorterDuff.Mode.SRC_ATOP);
    }

    @BindingAdapter(value = {"posterSrc", "defaultImage"}, requireAll = false)
    public static void setPosterImage(ImageView imageView, PosterModel posterModel, Drawable defaultView){
        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(imageView.getContext());
        if(posterModel != null && posterModel.getPosterUrl() != null){
            Drawable drawableFromPoster = movieSeriesBusinessService.getDrawableFromPoster(posterModel);
            imageView.setImageDrawable(drawableFromPoster);
        } else {
            imageView.setImageDrawable(defaultView);
        }
    }
}
