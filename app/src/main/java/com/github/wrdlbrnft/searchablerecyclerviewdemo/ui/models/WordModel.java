package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class WordModel {

    private final long mId;
    private final int mRank;
    private final String mWord;

    public WordModel(long id, int rank, String word) {
        mId = id;
        mRank = rank;
        mWord = word;
    }

    public long getId() {
        return mId;
    }

    public int getRank() {
        return mRank;
    }

    public String getWord() {
        return mWord;
    }
}
