package com.pathteam.hikeitv2.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.pathteam.hikeitv2.Adapters.HikeListAdapter;
import com.pathteam.hikeitv2.MainActivity;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nicholashall on 11/16/16.
 */

public class HikeListView extends LinearLayout {

    private Context context;
    private HikeListAdapter hikeAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public HikeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        hikeAdapter = new HikeListAdapter(new ArrayList<HikeList>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hikeAdapter);

        listHikes();
    }

    private void listHikes(){

        //This line gives us access to the Arraylist located in the main activity
        //Will contain all the information related to our hikes
        hikeAdapter.hikelist = new ArrayList<>(((MainActivity)context).hikelist);

        for (HikeList list : hikeAdapter.hikelist) {
            hikeAdapter.notifyDataSetChanged();
        }
    }
}