package fr.soat.demo.exo5_simplified_recyclerview.viewmodel;

import fr.soat.demo.moviesmodel.model.PosterModel;

/**
 * Created by yann_huriez on 10/02/17.
 */

public class PosterViewModel {

    private final PosterModel posterModel;

    public PosterViewModel(PosterModel posterModel) {
        this.posterModel = posterModel;
    }

    public PosterModel getPosterModel() {
        return posterModel;
    }

    // IMPORTANT : When implementing BindableViewModel, the layout to use is "view_card_poster", "not view_poster"
}
