package com.volodia.estafetatest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModuleApp.class})
public interface ComponentApp {

    void inject(MainActivity mainActivity);

}