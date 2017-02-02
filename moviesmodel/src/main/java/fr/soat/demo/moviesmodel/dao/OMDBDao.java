package fr.soat.demo.moviesmodel.dao;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class OMDBDao {

    public OMDBSearchResponse localSearch(Context context) {
        InputStream is = null;
        InputStreamReader reader = null;
        try {
            final AssetManager assets = context.getAssets();

            is = getDefaultInputStream(assets);
            reader = new InputStreamReader(is);

            final GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            return gson.fromJson(reader, OMDBSearchResponse.class);

        } catch (Exception e) {
            Log.d(TAG, "Error while parsing JSON into OMDBSearchResponse", e);
            return null;

        } finally {
            try {
                if(is != null){
                    is.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "Error while closing assets files", e);
            }
        }
    }

    public Drawable getPosterDrawable(Context context, String posterUrl){
        AssetManager assets = context.getAssets();
        String posterHash = Integer.toString(Math.abs(posterUrl.hashCode()));
        InputStream is = null;
        try {
            is = assets.open("posters/" + posterHash + ".png");
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            Log.d(TAG, "Cannot decode image for URL " + posterHash + " and hash : " + posterHash, e);
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private InputStream getDefaultInputStream(final AssetManager assets) throws IOException {
        String faqFile = "items.json";
        return assets.open(faqFile);
    }
}
