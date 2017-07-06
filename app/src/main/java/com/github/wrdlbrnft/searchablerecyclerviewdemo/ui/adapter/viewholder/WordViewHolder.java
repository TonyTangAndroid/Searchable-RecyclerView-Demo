package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.adapter.viewholder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;
import com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.models.WordModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_value)
    TextView tvValue;

    public WordViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    @SuppressLint("SetTextI18n")
    public void bind(final WordModel item) {

        tvRank.setText(item.getRank() + "");
        tvValue.setText(item.getWord());
    }
}
