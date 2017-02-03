package fr.soat.demo.moviesmodel.business.filters;

import java.util.List;

import fr.soat.demo.moviesmodel.model.MovieSeriesModel;

/**
 * Created by yann_huriez on 02/02/17.
 */

public class SearchFilter extends AbstractMovieSeriesFilter {

    private String searchInput;

    public SearchFilter(String searchInput) {
        this.searchInput = searchInput;
    }

    @Override
    public boolean isMatchingFilter(MovieSeriesModel model) {
        String field = model.getPosterModel().getTitle();
        if(field != null && field.toLowerCase().contains(searchInput.toLowerCase())){
            return true;
        }
        List<String> fields = model.getPosterModel().getActors();
        if(fields != null){
            for (String actor : fields) {
                if(actor.toLowerCase().contains(searchInput.toLowerCase())){
                    return true;
                }
            }
        }
        fields = model.getWriters();
        if(fields != null){
            for (String writer : fields) {
                if(writer.toLowerCase().contains(searchInput.toLowerCase())){
                    return true;
                }
            }
        }
        field = model.getDirector();
        return field != null && field.toLowerCase().contains(searchInput.toLowerCase());
    }


}
