package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private WordEntityAdapter adapter;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordEntityAdapter();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        List<WordEntity> emptyList = new ArrayList<>();
        adapter.setThings(emptyList);
        Pair<List<WordEntity>, DiffUtil.DiffResult> initialPair = Pair.create(emptyList, null);
        disposable = WordEntityRepository
                .latestThings(searchView)
                .scan(initialPair, this::getListDiffResultPair)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataReady);
        return true;
    }

    @DebugLog
    private void onDataReady(Pair<List<WordEntity>, DiffUtil.DiffResult> listDiffResultPair) {
        adapter.setThings(listDiffResultPair.first);
        listDiffResultPair.second.dispatchUpdatesTo(adapter);
    }

    @DebugLog
    @NonNull
    private Pair<List<WordEntity>, DiffUtil.DiffResult> getListDiffResultPair(Pair<List<WordEntity>, DiffUtil.DiffResult> pair, List<WordEntity> next) {
        WordEntityDiffCallback callback = new WordEntityDiffCallback(pair.first, next);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        testGetListDiffResultPair();
        return Pair.create(next, result);
    }

    @DebugLog
    private void testGetListDiffResultPair() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }


}
