package com.volodia.estafetatest;

import android.app.Application;
import android.content.Context;

import com.volodia.estafetatest.api.ComponentNet;
import com.volodia.estafetatest.api.DaggerComponentNet;
import com.volodia.estafetatest.api.ModuleNet;

public class AppEstafeta extends Application {

    private ComponentNet componentNet;
    private ComponentApp componentApp;

    public static AppEstafeta get(Context context) {
        return (AppEstafeta) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        componentApp = DaggerComponentApp.builder()
                .moduleApp(new ModuleApp(this))
                .build();
    }

    public ComponentApp getComponentApp() {
        return componentApp;
    }

    public ComponentNet getComponentNet() {
        return componentNet;
    }

    public ComponentNet createComponentNet(String login, String password) {
        componentNet = DaggerComponentNet.builder()
                .moduleNet(new ModuleNet(login, password))
                .build();
        return componentNet;
    }
}


