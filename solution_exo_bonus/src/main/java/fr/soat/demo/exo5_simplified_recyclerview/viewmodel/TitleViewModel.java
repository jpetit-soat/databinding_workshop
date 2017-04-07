package fr.soat.demo.exo5_simplified_recyclerview.viewmodel;

import fr.soat.demo.exo5_simplified_recyclerview.BR;
import fr.soat.demo.exo5_simplified_recyclerview.R;
import fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview.BindableViewModel;

/**
 * Created by yann_huriez on 10/02/17.
 */
// Note : this class is made as example of how the BindableViewModel interface should be implemented
public class TitleViewModel implements BindableViewModel {

    private String title;

    public TitleViewModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_title;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
