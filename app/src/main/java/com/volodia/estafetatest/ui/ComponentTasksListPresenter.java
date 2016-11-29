package com.volodia.estafetatest.ui;

import dagger.Subcomponent;



@FragmentScope
@Subcomponent(
        modules = ModuleTasksList.class
)
public interface ComponentTasksListPresenter {
     public void inject(FragmentTasksList fragmentTasksList);
}