package fr.soat.demo.exo3_dynamic_views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo3_dynamic_views.databinding.ViewFiltersBinding;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.business.filters.GenresFilter;
import fr.soat.demo.moviesmodel.business.filters.RatingFilter;
import fr.soat.demo.moviesmodel.business.filters.SearchFilter;
import fr.soat.demo.moviesmodel.business.filters.TypeFilter;
import fr.soat.demo.moviesmodel.model.CulturalType;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.utils.StringUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    // TODO Pass this boolean to "true" when ViewModel and binding are ready
    public static final boolean USING_DATABINDING = false;

    private MovieSeriesBusinessService movieSeriesBusinessService;

    private EditText filterSearchField;

    private SeekBar filterScoreSeekbar;
    private RatingBar filterRating;

    private EditText filterGenresField;
    private TextView filterGenresLabel;
    private ImageButton filterAddButton;
    private ImageButton filterDeleteButton;

    private RadioGroup filterRadioGroup;

    private Button filterSearchButton;

    private String searchString = null;
    private List<String> genres = new ArrayList<>();
    private CulturalType selectedType = null;
    private float rating = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieSeriesBusinessService = new MovieSeriesBusinessService(this);

        if(USING_DATABINDING){
            initWithTraditionnalMethod();
        } else {
            initWithDataBindingMethod();
        }
    }

    private void initWithTraditionnalMethod(){
        setContentView(R.layout.view_filters);

        initFields();

        // Init different listener to make the view dynamic and select some filters
        initListeners();

        // Update the count number of movies / series found using the filters
        updateSearchCount();
    }

    private void initWithDataBindingMethod() {
        ViewFiltersBinding binding = DataBindingUtil.setContentView(this, R.layout.view_filters);

        FiltersViewModel filtersViewModel = new FiltersViewModel(this);

        binding.setModel(filtersViewModel);
    }

    private void initFields() {
        filterSearchField = (EditText) findViewById(R.id.view_filter_search_field);

        filterScoreSeekbar = (SeekBar) findViewById(R.id.view_filter_score_seekbar);
        filterRating = (RatingBar) findViewById(R.id.view_filter_imdb_rating);

        filterGenresField = (EditText) findViewById(R.id.view_filter_genre_field);
        filterGenresLabel = (TextView) findViewById(R.id.view_filter_genres_label);
        filterAddButton = (ImageButton) findViewById(R.id.view_filter_add_genre_button);
        filterDeleteButton = (ImageButton) findViewById(R.id.view_filter_delete_genres_button);

        filterRadioGroup = (RadioGroup) findViewById(R.id.view_filter_radiogroup);

        filterSearchButton = (Button) findViewById(R.id.view_filter_search_button);
    }

    private void initListeners() {
        // Search
        filterSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchString = charSequence.toString();
                updateSearchCount();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Rating
        filterRating.setRating(0.0f);
        filterScoreSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                if(fromUser){
                    rating = value;
                    filterRating.setRating((float) value / 2.0f);
                    updateSearchCount();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Type
        filterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.view_filter_all_button:
                        selectedType = null;
                        filterGenresField.setHint(R.string.field_filter_genre_all_hint);
                        break;
                    case R.id.view_filter_movie_button:
                        selectedType = CulturalType.MOVIE;
                        filterGenresField.setHint(R.string.field_filter_genre_movie_hint);
                        break;
                    case R.id.view_filter_series_button:
                        selectedType = CulturalType.SERIES;
                        filterGenresField.setHint(R.string.field_filter_genre_series_hint);
                        break;
                }
                updateSearchCount();
            }
        });

        // Genres
        filterGenresLabel.setVisibility(GONE);
        filterAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newGenre = filterGenresField.getText().toString();
                genres.add(newGenre);
                filterGenresLabel.setText(StringUtils.assembleString(genres, ", "));
                filterGenresLabel.setVisibility(VISIBLE);
                filterGenresField.setText("");
                updateSearchCount();
            }
        });

        filterDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genres.clear();
                filterGenresLabel.setVisibility(GONE);
                updateSearchCount();
            }
        });

    }

    private void updateSearchCount() {
        List<AbstractMovieSeriesFilter> filters = new ArrayList<>();

        // Search
        if(!TextUtils.isEmpty(searchString)){
            filters.add(new SearchFilter(searchString));
        }

        // Type
        if(selectedType != null){
            filters.add(new TypeFilter(selectedType));
        }

        // Rating
        if(rating >= 0){
            filters.add(new RatingFilter(rating));
        }

        // Genres
        if(!genres.isEmpty()){
            filters.add(new GenresFilter(genres));
        }

        List<MovieSeriesModel> filteredMovieSeries = movieSeriesBusinessService.getFilteredMovieSeries(filters);
        String resultCount;
        if(filteredMovieSeries.isEmpty()){
            resultCount = getString(R.string.search_button_no_result);
        } else {
            resultCount = getResources().getQuantityString(R.plurals.search_button_result, filteredMovieSeries.size(), filteredMovieSeries.size());
        }
        filterSearchButton.setText(resultCount);
    }
}
