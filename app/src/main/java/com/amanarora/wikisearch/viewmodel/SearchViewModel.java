package com.amanarora.wikisearch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.amanarora.wikisearch.Injector;
import com.amanarora.wikisearch.SearchRepository;
import com.amanarora.wikisearch.model.Page;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;

    public SearchViewModel() {
        searchRepository = Injector.getInstance().getSearchRepository();
    }

    public LiveData<List<Page>> loadSearchResults(String query) {
        return searchRepository.loadSearchResults(query.trim());
    }
}
