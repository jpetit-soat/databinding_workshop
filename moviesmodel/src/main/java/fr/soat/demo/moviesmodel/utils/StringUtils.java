package fr.soat.demo.moviesmodel.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class StringUtils {

    public static List<String> createList(String field, String separator){
        if(TextUtils.isEmpty(field)){
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (String fieldToken : field.split(separator)) {
            result.add(fieldToken);
        }
        return result;
    }

    public static String assembleString(List<String> stringList, String separator){
        if(stringList.isEmpty()){
            return null;
        }

        String result = "";
        boolean first = true;
        for (String fieldToken : stringList) {
            if(!first){
                result += separator;
            }
            first = false;
            result += fieldToken;
        }
        return result;
    }
}
