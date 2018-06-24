package com.amanarora.wikisearch.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amanarora.wikisearch.R;
import com.amanarora.wikisearch.databinding.SearchResultListItemBinding;
import com.amanarora.wikisearch.model.Page;
import com.amanarora.wikisearch.model.Terms;
import com.amanarora.wikisearch.model.Thumbnail;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {

    private Context context;

    public SearchResultsAdapter(Context context) {
        this.context = context;
    }

    private List<Page> searchResults = new ArrayList<>();

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding =  DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.search_result_list_item,
                parent,
                false
        );
        return new SearchResultViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Page page = searchResults.get(position);
        holder.binding.resultTitleTextView.setText(page.getTitle());
        if (page.getTerms() != null) {
            Terms terms = page.getTerms();
            if (terms.getDescription() != null && !terms.getDescription().isEmpty()) {
                holder.binding.resultDescriptionTextView.setText(page.getTerms().getDescription().get(0));
            }
        }
        if (page.getThumbnail() != null) {
            Thumbnail thumbnail = page.getThumbnail();
            if (thumbnail.getSource() != null) {
                Glide.with(context)
                        .load(page.getThumbnail().getSource())
                        .into(holder.binding.resultImageView);
            }
       }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void updateList(List<Page> pages) {
        searchResults.clear();
        searchResults.addAll(pages);
        notifyDataSetChanged();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        SearchResultListItemBinding binding;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
