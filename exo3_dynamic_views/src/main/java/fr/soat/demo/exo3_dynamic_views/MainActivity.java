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
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import fr.soat.demo.exo3_dynamic_views.databinding.ViewFiltersBinding;
import fr.soat.demo.moviesmodel.business.MovieSeriesBusinessService;
import fr.soat.demo.moviesmodel.business.filters.AbstractMovieSeriesFilter;
import fr.soat.demo.moviesmodel.business.filters.RatingFilter;
import fr.soat.demo.moviesmodel.business.filters.SearchFilter;
import fr.soat.demo.moviesmodel.business.filters.TypeFilter;
import fr.soat.demo.moviesmodel.model.CulturalType;
import fr.soat.demo.moviesmodel.model.MovieSeriesModel;
import fr.soat.demo.moviesmodel.utils.PopupUtils;

public class MainActivity extends AppCompatActivity {

    // TODO Pass this boolean to "true" when ViewModel and binding are ready
    public static final boolean USING_DATABINDING = false;

    private MovieSeriesBusinessService movieSeriesBusinessService;

    private EditText filterSearchField;

    private SeekBar filterScoreSeekbar;
    private RatingBar filterRating;

    private RadioGroup filterRadioGroup;

    private Button filterSearchButton;

    private String searchString = null;
    private CulturalType selectedType = null;
    private float rating = 0.0f;
    private int totalResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieSeriesBusinessService = new MovieSeriesBusinessService(this);

        if(!USING_DATABINDING){
            initWithTraditionalMethod();
        } else {
            initWithDataBindingMethod();
        }
    }

    private void initWithTraditionalMethod(){
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

        filterRadioGroup = (RadioGroup) findViewById(R.id.view_filter_global_radio_group);

        filterSearchButton = (Button) findViewById(R.id.view_filter_result_button);
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
        filterScoreSeekbar.setProgress(0);
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
        filterRadioGroup.check(convertCulturalTypeToButtonId(selectedType));
        filterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                selectedType = convertButtonIdToCulturalType(radioGroup.getCheckedRadioButtonId());
                updateSearchCount();
            }
        });

        // Result
        filterSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupUtils.showPopupForItemCount(MainActivity.this, view, totalResult);
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

        List<MovieSeriesModel> filteredMovieSeries = movieSeriesBusinessService.getFilteredMovieSeries(filters);
        totalResult = filteredMovieSeries.size();

        String resultCount;
        if(totalResult == 0){
            resultCount = getString(R.string.search_button_no_result);
        } else {
            resultCount = getResources().getQuantityString(R.plurals.search_button_result, totalResult, totalResult);
        }
        filterSearchButton.setText(resultCount);
    }


    public int convertCulturalTypeToButtonId(CulturalType type){
        if(type != null) {
            switch (type) {
                case MOVIE:
                    return R.id.view_filter_movie_button;
                case SERIES:
                    return R.id.view_filter_series_button;
            }
        }
        return R.id.view_filter_all_button;
    }

    public CulturalType convertButtonIdToCulturalType(int buttonId){
        switch (buttonId){
            case R.id.view_filter_all_button:
                return null;
            case R.id.view_filter_movie_button:
                return CulturalType.MOVIE;
            case R.id.view_filter_series_button:
                return CulturalType.SERIES;
        }
        return null;
    }
}
