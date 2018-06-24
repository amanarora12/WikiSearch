package com.amanarora.wikisearch.repository;

import com.amanarora.wikisearch.model.Result;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface WikipediaService {
    @GET("api.php")
    Observable<Result> getSearchResults(@QueryMap Map<String, String> params);
}
