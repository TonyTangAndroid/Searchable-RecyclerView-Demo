package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

import java.util.List;

public class WordEntityAdapter extends RecyclerView.Adapter<WordEntityViewHolder> {


    private List<WordEntity> wordModelList;
    private List<WordEntity> filterWordModelList;

    public List<WordEntity> getWordModelList() {
        return wordModelList;
    }

    public void setWordModelList(List<WordEntity> wordModelList) {
        this.wordModelList = wordModelList;
        this.filterWordModelList = wordModelList;
        notifyDataSetChanged();
    }

    public List<WordEntity> getFilterWordModelList() {
        return filterWordModelList;
    }

    public void setFilterWordModelList(List<WordEntity> filterWordModelList) {
        this.filterWordModelList = filterWordModelList;
        notifyDataSetChanged();

    }

    @Override
    public WordEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordEntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordEntityViewHolder holder, int position) {
        holder.bind(getFilterWordModelList().get(position));
    }

    @Override
    public int getItemCount() {
        return filterWordModelList != null ? filterWordModelList.size() : 0;
    }
}
