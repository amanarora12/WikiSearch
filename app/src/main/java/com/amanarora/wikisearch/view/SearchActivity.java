package com.amanarora.wikisearch.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.amanarora.wikisearch.R;
import com.amanarora.wikisearch.databinding.ActivitySearchBinding;
import com.amanarora.wikisearch.model.Page;
import com.amanarora.wikisearch.viewmodel.SearchViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private SearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        setSupportActionBar(binding.toolbar);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        setupSearchResultsRecyclerView();
    }

    private void setupSearchResultsRecyclerView() {
        binding.content.resultList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultsAdapter(this);
        binding.content.resultList.setAdapter(adapter);
        setupSearch();
    }

    private void setupSearch() {
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setQuery(newText);
                processQuery(newText);
                return true;
            }
        });
    }

    private void processQuery(String query) {
        viewModel.loadSearchResults(query).observe(SearchActivity.this, new Observer<List<Page>>() {
            @Override
            public void onChanged(@Nullable List<Page> pages) {
                if (pages != null) {
                    adapter.updateList(pages);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        binding.searchView.setMenuItem(item);
        binding.searchView.setQuery(viewModel.getQuery().getValue(), true);
        return true;
    }
}
