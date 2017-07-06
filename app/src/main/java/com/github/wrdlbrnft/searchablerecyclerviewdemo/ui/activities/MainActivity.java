package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<WordEntity> wordEntityArrayList;
    private WordEntityAdapter adapter;
    private Disposable disposable;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WordEntityAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.post(this::startSearchObservation);
    }


    private void startSearchObservation() {
        disposable = RxSearchView.queryTextChanges(searchView)
                .startWith("")
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap(new Function<CharSequence, ObservableSource<? extends List<WordEntity>>>() {
                    @Override
                    public ObservableSource<List<WordEntity>> apply(@NonNull CharSequence charSequence) throws Exception {
                        return Observable.fromCallable(() -> doFilter(charSequence)).subscribeOn(Schedulers.computation());
                    }
                })
                .scan(Pair.create(Collections.emptyList(), null), this::doCalculateDiff)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged(listDiffResultPair -> listDiffResultPair.first)
                .subscribe(this::onDataReady);

    }

    private List<WordEntity> getFilteredList(String query) {

        Collection<? extends WordEntity> models = initAndGetWordEntityList();
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


    private List<WordEntity> initAndGetWordEntityList() {
        if (wordEntityArrayList == null) {
            wordEntityArrayList = new ArrayList<>();
            final String[] words = getResources().getStringArray(R.array.words_less);
            for (int i = 0; i < words.length; i++) {
                wordEntityArrayList.add(new WordEntity(i, i + 1, words[i]));
            }
        }
        return wordEntityArrayList;
    }

    @DebugLog
    private List<WordEntity> doFilter(CharSequence searchViewQueryTextEvent) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            //if it is switchMap, the thread will be interrupted and the result will not be ignored in the downstream.
            // If it is flatMap, the thread won't be interrupted and the result will be ignored in the downstream except the latest one, which is exactly what we need in search use case.
            // e.printStackTrace();
        }
        return getFilteredList(searchViewQueryTextEvent.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }

    @DebugLog
    private void onDataReady(Pair<List<WordEntity>, DiffUtil.DiffResult> listDiffResultPair) {
        adapter.setDataList(listDiffResultPair.first);
        listDiffResultPair.second.dispatchUpdatesTo(adapter);
    }

    @DebugLog
    @NonNull
    private Pair<List<WordEntity>, DiffUtil.DiffResult> doCalculateDiff(Pair<List<WordEntity>, DiffUtil.DiffResult> pair, List<WordEntity> next) {
        WordEntityDiffCallback callback = new WordEntityDiffCallback(pair.first, next);
        Log.d("MainActivity", "raw data source size : " + pair.first.size());
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        return Pair.create(next, result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

}
