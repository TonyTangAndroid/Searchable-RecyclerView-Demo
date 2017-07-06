package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.content.Context;
import android.text.TextUtils;
import android.widget.SearchView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WordEntityRepository {

    private static List<WordEntity> wordEntityArrayList;


    private static List<WordEntity> filter(String query) {

        testFilter();
        Collection<? extends WordEntity> models = getAllWordEntityList(WordApplication.getInstance());
        if (TextUtils.isEmpty(query)) {
            return new ArrayList<>(models);
        }
        final String lowerCaseQuery = query.toLowerCase();

        final List<WordEntity> filteredModelList = new ArrayList<>();
        for (WordEntity model : models) {
            final String text = model.getWord().toLowerCase();
            final String rank = String.valueOf(model.getRank());
            if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @DebugLog
    private static void testFilter() {

    }

    public static Observable<List<WordEntity>> latestThings(SearchView searchView) {

        return RxSearchView.queryTextChanges(searchView)
                .startWith("")
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap(new Function<CharSequence, ObservableSource<? extends List<WordEntity>>>() {
                    @Override
                    public ObservableSource<List<WordEntity>> apply(@NonNull CharSequence charSequence) throws Exception {
                        return Observable.fromCallable(() -> doFilter(charSequence)).subscribeOn(Schedulers.computation());
                    }
                });

    }


    private static List<WordEntity> getAllWordEntityList(Context context) {
        if (wordEntityArrayList == null) {
            wordEntityArrayList = new ArrayList<>();
            final String[] words = context.getResources().getStringArray(R.array.words_less);
            for (int i = 0; i < words.length; i++) {
                wordEntityArrayList.add(new WordEntity(i, i + 1, words[i]));
            }
        }
        return wordEntityArrayList;
    }

    private static List<WordEntity> doFilter(CharSequence searchViewQueryTextEvent) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            //if it is switchMap, the thread will be interrupted and the result will not be ignored in the downstream.
//            // If it is flatMap, the thread won't be interrupted and the result will be ignored in the downstream except the latest one, which is exactly what we need in search use case.
//            e.printStackTrace();
//        }
        return filter(searchViewQueryTextEvent.toString());
    }
}
