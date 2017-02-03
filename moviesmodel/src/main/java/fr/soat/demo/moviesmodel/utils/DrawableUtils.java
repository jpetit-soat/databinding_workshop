package fr.soat.demo.moviesmodel.utils;

import android.support.annotation.DrawableRes;

import fr.soat.demo.moviesmodel.R;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class DrawableUtils {

    public static @DrawableRes int getCountryDrawable(String country){
        switch (country){
            case "USA":
                return R.drawable.us;
            case "UK":
                return R.drawable.gb;
            case "Germany":
                return R.drawable.de;
            case "Canada":
                return R.drawable.ca;
            case "France":
                return R.drawable.fr;
        }
        return -1;
    }
}
