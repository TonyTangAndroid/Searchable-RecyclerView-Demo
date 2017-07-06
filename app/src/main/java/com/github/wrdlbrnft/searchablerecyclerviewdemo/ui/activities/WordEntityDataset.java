package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ztang on 7/6/17.
 */
public class WordEntityDataset {

    SortedList<WordEntity> sortedList = null;

    public WordEntityDataset(final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
        this.sortedList = new SortedList<>(WordEntity.class,
                new SortedList.BatchedCallback<>(new SortedListAdapterCallback<WordEntity>(adapter) {
                    @Override
                    public int compare(WordEntity a1, WordEntity a2) {
                        return getComparator().compare(a1, a2);
                    }

                    @Override
                    public boolean areContentsTheSame(WordEntity oldItem, WordEntity newItem) {
                        return oldItem.areContentsTheSame(newItem);
                    }

                    @Override
                    public boolean areItemsTheSame(WordEntity item1, WordEntity item2) {
                        return item1.areItemsTheSame(item2);
                    }

                    @Override
                    public void onInserted(int position, int count) {
                        super.onInserted(position, count);
                        recyclerView.scrollToPosition(position);
                    }
                }));
    }


    public int size() {
        return sortedList.size();
    }

    public void changeData(List<WordEntity> dataList) {
        //?? should beginBatchedUpdates // TODO: 7/6/17
        sortedList.clear();
        sortedList.addAll(dataList);
        sortedList.endBatchedUpdates();
    }

    public WordEntity getWordEntity(int position) {
        return sortedList.get(position);
    }

    public void remove(WordEntity article) {
        sortedList.beginBatchedUpdates();
        sortedList.remove(article);
        sortedList.endBatchedUpdates();
    }

    public void add(WordEntity article) {
        sortedList.beginBatchedUpdates();
        sortedList.add(article);
        sortedList.endBatchedUpdates();
    }

    private Comparator<WordEntity> getComparator() {
        return WordEntity.wordEntityRankComparator;
    }
}
