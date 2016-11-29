package com.volodia.estafetatest;

import android.app.Application;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class ModuleApp {
    private Application application;

    public ModuleApp(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }


    @Provides
    @Singleton
    public PrefsManager providePreferences() {
        return new PrefsManager(application);
    }




}