package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordEntityViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_value)
    TextView tvValue;

    public WordEntityViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    @SuppressLint("SetTextI18n")
    public void bind(final WordEntity item) {

        tvRank.setText(item.getRank() + "");
        tvValue.setText(item.getWord());
    }
}
