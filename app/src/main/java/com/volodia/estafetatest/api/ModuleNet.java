package com.volodia.estafetatest.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Volodia on 28.11.2016.
 */
@Module
public class ModuleNet {


    String login;
    String password;

    public ModuleNet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Provides
    @Singleton
    public ApiService provideRestAdapter() {
        return  ServiceGenerator.createService(ApiService.class, login, password);
    }
}
