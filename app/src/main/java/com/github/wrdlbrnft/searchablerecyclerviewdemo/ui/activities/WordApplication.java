package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.app.Application;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class WordApplication extends Application {

    private static WordApplication instance;

    public static WordApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
