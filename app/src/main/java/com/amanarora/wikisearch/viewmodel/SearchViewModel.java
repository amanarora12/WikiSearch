package com.amanarora.wikisearch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amanarora.wikisearch.di.Injector;
import com.amanarora.wikisearch.repository.SearchRepository;
import com.amanarora.wikisearch.model.Page;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> query = new MutableLiveData<>();

    private SearchRepository searchRepository;

    public SearchViewModel() {
        searchRepository = Injector.getInstance().getSearchRepository();
    }

    public LiveData<List<Page>> loadSearchResults(String query) {
        return searchRepository.loadSearchResults(query.trim());
    }

    public void setQuery(String queryData) {
        query.setValue(queryData);
    }

    public LiveData<String> getQuery() {
        return query;
    }
}
