package fr.soat.demo.moviesmodel;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class DateUtils {

    public static String formatDateToString(Date date, String formatPattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern, Locale.US);
        return simpleDateFormat.format(date);
    }

    public static Date formatStringToDate(String stringDate, String formatPattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern, Locale.US);
        try {
            return simpleDateFormat.parse(stringDate);

        } catch (ParseException e) {
            Log.e(DateUtils.class.getSimpleName(), "Cannot parse date " + stringDate + " with pattern " + formatPattern, e);
        }
        return null;
    }
}
