package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztang on 7/6/17.
 */
class WordEntityAdapter extends RecyclerView.Adapter<WordEntityViewHolder> {
    private List<WordEntity> things = new ArrayList<>(); // Start with empty list

    @Override
    public WordEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordEntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordEntityViewHolder holder, int position) {
        WordEntity thing = things.get(position);
        holder.bind(thing);
    }

    @Override
    public int getItemCount() {
        return things.size();
    }

    public void setThings(List<WordEntity> things) {
        this.things = things;
    }
}
