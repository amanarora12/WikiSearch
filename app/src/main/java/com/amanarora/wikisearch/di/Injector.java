package com.amanarora.wikisearch.di;

import com.amanarora.wikisearch.Constants;
import com.amanarora.wikisearch.repository.SearchRepository;
import com.amanarora.wikisearch.repository.WikipediaService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injector {

    private static volatile Injector injector;
    private Retrofit retrofit;
    private WikipediaService wikipediaService;
    private SearchRepository searchRepository;

    private Injector() {}

    public static Injector getInstance() {
        if (injector == null) {
            synchronized (Injector.class) {
                if (injector == null) {
                    injector = new Injector();
                }
            }
        }
        return injector;
    }

    public void setup() {
        retrofit = initRetrofit(Constants.BASE_URL);
        wikipediaService = initWikipediaService();
        searchRepository = new SearchRepository(wikipediaService);
    }

    private Retrofit initRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private WikipediaService initWikipediaService() {
        return retrofit.create(WikipediaService.class);
    }

    public WikipediaService getWikipediaService() {
        return wikipediaService;
    }

    public SearchRepository getSearchRepository() {
        return searchRepository;
    }
}
