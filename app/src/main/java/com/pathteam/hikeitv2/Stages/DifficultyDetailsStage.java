package com.pathteam.hikeitv2.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/22/16.
 */

public class DifficultyDetailsStage extends IndexedStage {
    private final SlideRigger rigger;
    public DifficultyDetailsStage(Application context){
        super(CaloriesBurnedStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public DifficultyDetailsStage(){
        this(HikeApplication.getInstance());
    }
    @Override
    public int getLayoutId() {
        return R.layout.difficulty_details_view;
    }
    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
