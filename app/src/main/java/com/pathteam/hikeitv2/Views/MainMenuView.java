package com.pathteam.hikeitv2.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Stages.CaloriesBurnedStage;
import com.pathteam.hikeitv2.Stages.HikeItMapStage;
import com.pathteam.hikeitv2.Stages.HikeListStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
/**
 * Created by nicholashall on 11/16/16.
 */
public class MainMenuView extends RelativeLayout{
    private Context context;
    @Bind(R.id.take_a_hike)
    TextView takeAHike;
    @Bind(R.id.hike_list)
    TextView hikeList;
    @Bind(R.id.my_stats)
    TextView myStats;

    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
// this is the animated background logic. we attach a variable to the resource in the view.
        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        //Changes the direction the screen scrolls - Right to Left Scrolling
        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        // we set this to go forever could be set to a number like 1 or 100
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());

        //2 Seconds Mountain Scrolling
        animator.setDuration(20000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // the progress will update and we will use that number to changee the x position on the screen.
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
// This starts the background image

        animator.start();
        final ImageView overlayOne = (ImageView) findViewById(R.id.cloud_one);
        final ImageView overlayTwo = (ImageView) findViewById(R.id.cloud_two);
        final ValueAnimator animator2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        //5 Seconds Cloud Scrolling
        animator2.setDuration(50000L);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = overlayOne.getWidth();
                final float translationX = width * progress;
                overlayOne.setTranslationX(translationX);
                overlayTwo.setTranslationX(translationX - width);
            }
        });
        animator2.start();

    }

    @OnClick(R.id.take_a_hike)
    public void showHikeView(){
        Flow flow = HikeApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new HikeItMapStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.hike_list)
    public void showHikeListView(){
        Flow flow = HikeApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new HikeListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.my_stats)
    public void showStatsView(){
        Flow flow = HikeApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaloriesBurnedStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

}