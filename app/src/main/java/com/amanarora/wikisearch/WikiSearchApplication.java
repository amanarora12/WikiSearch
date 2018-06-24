package com.amanarora.wikisearch;

import android.app.Application;

import com.amanarora.wikisearch.di.Injector;

public class WikiSearchApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Injector injector = Injector.getInstance();
        injector.setup();
    }
}
