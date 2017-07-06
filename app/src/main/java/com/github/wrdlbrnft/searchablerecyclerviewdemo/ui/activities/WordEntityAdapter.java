package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

public class WordEntityAdapter extends RecyclerView.Adapter<WordEntityViewHolder> {
    private WordEntityDataset dataset;

    public void articleDataset(WordEntityDataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public WordEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordEntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordEntityViewHolder holder, int position) {
        WordEntity wordEntity = dataset.getWordEntity(position);
        holder.bind(wordEntity);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
