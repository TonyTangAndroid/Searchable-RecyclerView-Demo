package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.ExampleAdapter;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WordModel;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ExampleAdapter adapter;
    private Disposable subscribe;

    private List<WordModel> filter(List<WordModel> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<WordModel> filteredModelList = new ArrayList<>();
        for (WordModel model : models) {
            final String text = model.getWord().toLowerCase();
            final String rank = String.valueOf(model.getRank());
            if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ExampleAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<WordModel> mModels = new ArrayList<>();
        final String[] words = getResources().getStringArray(R.array.words);
        for (int i = 0; i < words.length; i++) {
            mModels.add(new WordModel(i, i + 1, words[i]));
        }
        adapter.setWordModelList(mModels);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Observable<List<WordModel>> listObservable = getListObservableWithSwitchMap(searchView);
//        Observable<List<WordModel>> listObservable = getListObservableWithFlatMap(searchView);
        subscribe = listObservable.subscribe(this::onNewResultReady);
        return true;
    }

    private Observable<List<WordModel>> getListObservableWithSwitchMap(SearchView searchView) {
        return RxSearchView.queryTextChanges(searchView)

                .switchMap(new Function<CharSequence, ObservableSource<? extends List<WordModel>>>() {
                    @Override
                    public ObservableSource<List<WordModel>> apply(@NonNull CharSequence charSequence) throws Exception {
                        return Observable.fromCallable(() -> doFilter(charSequence)).subscribeOn(Schedulers.computation());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    @SuppressWarnings("unused")
    private Observable<List<WordModel>> getListObservableWithFlatMap(SearchView searchView) {
        return RxSearchView.queryTextChanges(searchView)

                .flatMap(new Function<CharSequence, ObservableSource<? extends List<WordModel>>>() {
                    @Override
                    public ObservableSource<List<WordModel>> apply(@NonNull CharSequence charSequence) throws Exception {
                        return Observable.fromCallable(() -> doFilter(charSequence)).subscribeOn(Schedulers.computation());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    private void onNewResultReady(List<WordModel> wordModels) {
        Log.d("MainActivity", " new data size:" + wordModels.size());
        adapter.setFilterWordModelList(wordModels);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }

    @DebugLog
    private List<WordModel> doFilter(CharSequence searchViewQueryTextEvent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //if it is switchMap, the thread will be interrupted and the result will not be ignored in the downstream.
            // If it is flatMap, the thread won't be interrupted and the result will be ignored in the downstream except the latest one, which is exactly what we need in search use case.
            e.printStackTrace();
        }
        return filter(adapter.getWordModelList(), searchViewQueryTextEvent.toString());
    }


}
