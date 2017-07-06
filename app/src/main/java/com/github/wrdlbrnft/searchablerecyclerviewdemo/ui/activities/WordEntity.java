package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class WordEntity {

    private final long mId;
    private final int mRank;
    private final String mWord;

    public WordEntity(long id, int rank, String word) {
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


    @Override
    public String toString() {
        return "";
    }
}
