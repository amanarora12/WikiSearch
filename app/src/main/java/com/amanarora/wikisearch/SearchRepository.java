package com.amanarora.wikisearch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.amanarora.wikisearch.model.Page;
import com.amanarora.wikisearch.model.Query;
import com.amanarora.wikisearch.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchRepository {
    private static final String LOG_TAG = SearchRepository.class.getSimpleName();

    private WikipediaService wikipediaService;

    public SearchRepository(WikipediaService wikipediaService) {
        this.wikipediaService = wikipediaService;
    }

    public LiveData<List<Page>> loadSearchResults(String query) {
        final MutableLiveData<List<Page>> data = new MutableLiveData<>();
        Map<String, String> params = new HashMap<>();
        params.put("action", "query");
        params.put("format", "json");
        params.put("prop", "pageimages|pageterms");
        params.put("generator", "prefixsearch");
        params.put("formatversion", String.valueOf(2));
        params.put("piprop", "thumbnail");
        params.put("pithumbsize", String.valueOf(50));
        params.put("pilimit", String.valueOf(10));
        params.put("wbptterms", "description");
        params.put("gpssearch", query);
        params.put("gpslimit", String.valueOf(10));
        wikipediaService.getSearchResults(params)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (result != null) {
                            Query query = result.getQuery();
                            if (query != null) {
                                List<Page> pages = query.getPages();
                                for (Page page : pages) {
                                    Log.d(LOG_TAG, "onResponse: Page " + page.getTitle());
                                }
                                data.setValue(pages);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "onFailure: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}
