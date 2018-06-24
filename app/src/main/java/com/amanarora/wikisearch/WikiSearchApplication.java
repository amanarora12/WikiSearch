package com.amanarora.wikisearch;

import android.app.Application;

public class WikiSearchApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Injector injector = Injector.getInstance();
        injector.setup();
    }
}
