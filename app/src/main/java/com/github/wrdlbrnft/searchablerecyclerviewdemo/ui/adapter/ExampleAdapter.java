package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder.WordViewHolder;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WordModel;

import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class ExampleAdapter extends RecyclerView.Adapter<WordViewHolder> {


    private List<WordModel> wordModelList;
    private List<WordModel> filterWordModelList;

    public List<WordModel> getWordModelList() {
        return wordModelList;
    }

    public void setWordModelList(List<WordModel> wordModelList) {
        this.wordModelList = wordModelList;
        this.filterWordModelList = wordModelList;
        notifyDataSetChanged();
    }

    public List<WordModel> getFilterWordModelList() {
        return filterWordModelList;
    }

    public void setFilterWordModelList(List<WordModel> filterWordModelList) {
        this.filterWordModelList = filterWordModelList;
    }

    @DebugLog
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        holder.bind(getFilterWordModelList().get(position));
    }

    @Override
    public int getItemCount() {
        return filterWordModelList != null ? filterWordModelList.size() : 0;
    }
}
