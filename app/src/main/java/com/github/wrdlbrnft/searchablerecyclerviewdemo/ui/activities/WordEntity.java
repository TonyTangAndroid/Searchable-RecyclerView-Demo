package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

public class WordEntity {

    private final long id;
    private final int rank;
    private final String word;

    public WordEntity(long id, int rank, String word) {
        this.id = id;
        this.rank = rank;
        this.word = word;
    }

    public long getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "";
    }
}
