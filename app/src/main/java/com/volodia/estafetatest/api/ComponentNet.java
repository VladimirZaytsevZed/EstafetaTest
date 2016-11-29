package com.volodia.estafetatest.api;

import com.volodia.estafetatest.ui.ComponentTasksListPresenter;
import com.volodia.estafetatest.ui.ModuleTasksList;
import com.volodia.estafetatest.ui.PresenterLogin;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Volodia on 28.11.2016.
 */

@Singleton
@Component(modules = {ModuleNet.class})
public interface ComponentNet {

    void inject(PresenterLogin presenterLogin);
    ComponentTasksListPresenter plus(ModuleTasksList moduleTasksList);

}

