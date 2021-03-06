package fr.soat.demo.moviesmodel.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.util.Date;
import java.util.List;

import fr.soat.demo.moviesmodel.R;
import fr.soat.demo.moviesmodel.dao.OMDBItem;
import fr.soat.demo.moviesmodel.utils.DateUtils;
import fr.soat.demo.moviesmodel.utils.StringUtils;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class PosterModel {

    private final String posterUrl;

    private final CulturalType type;

    private final String title;
    private final Date releaseYear;
    private final Date endYear;
    private final Date releaseDate;
    private final List<String> actors;
    private final List<String> genres;

    private Drawable imageLoaded;


    public PosterModel(OMDBItem response) {
        this.posterUrl = response.Poster.equalsIgnoreCase("N/A") ? null : response.Poster;
        this.title = response.Title;
        this.actors = StringUtils.createList(response.Actors, ", ");
        this.genres = StringUtils.createList(response.Genre, ", ");
        this.type = CulturalType.typeFromString(response.Type);

        if(response.Released != null && !response.Released.equalsIgnoreCase("N/A")){
            this.releaseDate = DateUtils.formatStringToDate(response.Released, "dd MMM yyyy");
        } else {
            this.releaseDate = null;
        }

        // Years
        if(!TextUtils.isEmpty(response.Year) && response.Year.contains("-")){
            String[] beginEndDates = response.Year.split("-");
            this.releaseYear = DateUtils.formatStringToDate(beginEndDates[0], "yyyy");
            if(beginEndDates.length > 1 && !TextUtils.isEmpty(beginEndDates[1])){
                this.endYear = DateUtils.formatStringToDate(beginEndDates[1], "yyyy");
            } else {
                this.endYear = null;
            }
        } else {
            this.releaseYear = DateUtils.formatStringToDate(response.Year, "yyyy");
            this.endYear = null;
        }
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public List<String> getActors() {
        return actors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Date getEndYear() {
        return endYear;
    }

    public CulturalType getType() {
        return type;
    }

    public Drawable getImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(Drawable imageLoaded) {
        this.imageLoaded = imageLoaded;
    }

    // //////////////////
    // Utility methods //
    // /////////////// //

    public String getFormattedGenres(){
        return StringUtils.assembleString(genres, ", ");
    }

    public String getFormattedActors(Context context){
        return context.getString(R.string.format_actors, StringUtils.assembleString(actors, ", "));
    }

    public String getFormattedDate(Context context){
        Date releaseDate = getReleaseDate();
        if(releaseDate != null){
            return DateUtils.formatDateToString(releaseDate, context.getString(R.string.date_format_release));
        }
        return null;
    }
}
