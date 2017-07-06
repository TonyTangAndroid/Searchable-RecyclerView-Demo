package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class WordEntityDiffCallback extends DiffUtil.Callback {
    private List<WordEntity> current;
    private List<WordEntity> next;

    public WordEntityDiffCallback(List<WordEntity> current, List<WordEntity> next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public int getOldListSize() {
        return current.size();
    }

    @Override
    public int getNewListSize() {
        return next.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        WordEntity currentItem = current.get(oldItemPosition);
        WordEntity nextItem = next.get(newItemPosition);
        return currentItem.getId() == nextItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        WordEntity currentItem = current.get(oldItemPosition);
        WordEntity nextItem = next.get(newItemPosition);
        return currentItem.equals(nextItem);
    }
}
