package com.pathteam.hikeitv2.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Stages.CaloriesBurnedStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;

/**
 * Created by jeremiahlewis on 11/22/16.
 */

public class DifficultyDetailsView extends LinearLayout {

    public Context context;

    @Bind(R.id.confirm_button)
    Button confirmButton;

    public DifficultyDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.confirm_button)
    public void goBackwards(){
        Flow flow = HikeApplication.getMainFlow();
        flow.setHistory(History.single(new CaloriesBurnedStage()),
                Flow.Direction.FORWARD);
        }
    }
