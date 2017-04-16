package com.pathteam.hikeitv2.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Riggers.SlideRigger;

/**
 * Created by JoshuaMabry on 11/18/16.
 */

public class CaloriesBurnedStage extends IndexedStage {

    private final SlideRigger rigger;
    public CaloriesBurnedStage(Application context){
        super(HikeListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public CaloriesBurnedStage(){
        this(HikeApplication.getInstance());
    }
    @Override
    public int getLayoutId() {
        return R.layout.calories_burned_view;
    }
    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
