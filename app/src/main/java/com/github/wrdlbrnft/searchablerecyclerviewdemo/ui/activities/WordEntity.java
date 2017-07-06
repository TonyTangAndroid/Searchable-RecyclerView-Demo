package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import java.util.Comparator;

public class WordEntity {
    public static final WordEntityRankComparator wordEntityRankComparator = new WordEntityRankComparator();
    private final long id;
    private final int rank;
    private final String word;

    public WordEntity(long id, int rank, String word) {
        this.id = id;
        this.rank = rank;
        this.word = word;
    }

    public boolean areContentsTheSame(WordEntity article) {
        return this.equals(article);
    }

    public boolean areItemsTheSame(WordEntity article) {
        return this.getId() == article.getId();
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

    public static class WordEntityRankComparator implements Comparator<WordEntity> {
        @Override
        public int compare(WordEntity first, WordEntity second) {
            return Integer.compare(first.getRank(), second.getRank());
        }
    }

}
