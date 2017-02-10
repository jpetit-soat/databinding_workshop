package fr.soat.demo.exo5_simplified_recyclerview.bindingrecyclerview;

import android.support.annotation.LayoutRes;

/**
 * Created by yann_huriez on 03/10/16.
 */

public interface BindableViewModel{

    @LayoutRes int getLayoutRes();

    int getVariableId();
}
