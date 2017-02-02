package fr.soat.demo.moviesmodel.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class DownloadPosterASyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public DownloadPosterASyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        MovieSeriesBusinessService movieSeriesBusinessService = new MovieSeriesBusinessService(context);
        List<MovieSeriesModel> allMovieSeries = movieSeriesBusinessService.getAllMovieSeries();
        downloadAllPosters(allMovieSeries);

        return null;
    }

    public void downloadAllPosters(List<MovieSeriesModel> models) {

        final File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        final String externalPath = externalStorageDirectory.getAbsolutePath();

        for (MovieSeriesModel model : models) {
            final String posterUrl = model.getPosterModel().getPosterUrl();

            if (posterUrl.equals("N/A")) {
                continue;
            }

            FileOutputStream out = null;
            try {
                java.net.URL url = new java.net.URL(posterUrl);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                String filepath = externalPath + "/" + Integer.toString(Math.abs(posterUrl.hashCode())) + ".png";
                Log.i(getClass().getSimpleName(), "Filepath : " + filepath);
                out = new FileOutputStream(filepath);
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                Log.i(getClass().getSimpleName(), "File saved");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
