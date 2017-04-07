package fr.soat.demo.exo5_simplified_recyclerview.viewmodel;

import fr.soat.demo.exo5_simplified_recyclerview.BR;
import fr.soat.demo.exo5_simplified_recyclerview.R;
import fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview.BindableViewModel;
import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class PosterViewModel implements BindableViewModel{

    private final PosterModel posterModel;

    public PosterViewModel(PosterModel posterModel) {
        this.posterModel = posterModel;
    }

    public PosterModel getPosterModel() {
        return posterModel;
    }

    // IMPORTANT : When implementing BindableViewModel, the layout to use is "view_card_poster", "not view_poster"
    @Override
    public int getLayoutRes() {
        return R.layout.view_card_poster;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
