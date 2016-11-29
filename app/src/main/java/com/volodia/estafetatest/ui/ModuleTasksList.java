package com.volodia.estafetatest.ui;

import com.volodia.estafetatest.Router;
import com.volodia.estafetatest.api.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Volodia on 28.11.2016.
 */
@Module
public class ModuleTasksList {
    private FragmentTasksList fragmentTasksList;
    private Router router;


    public ModuleTasksList(FragmentTasksList fragmentTasksList, Router router) {
        this.fragmentTasksList = fragmentTasksList;
        this.router = router;
    }

    @Provides
    @FragmentScope
    PresenterTasksList providePresenterTasksList(ApiService apiService) {
        return new PresenterTasksList(fragmentTasksList, apiService, router);
    }
}
