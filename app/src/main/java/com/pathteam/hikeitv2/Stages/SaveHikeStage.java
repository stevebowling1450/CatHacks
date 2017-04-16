package com.pathteam.hikeitv2.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Riggers.SlideRigger;

/**
 * Created by jeremiahlewis on 11/18/16.
 */

public class SaveHikeStage extends IndexedStage {
    private final SlideRigger rigger;
    public SaveHikeStage(Application context){
        super(HikeListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public SaveHikeStage(){
        this(HikeApplication.getInstance());
    }
    @Override
    public int getLayoutId() {
        return R.layout.save_hike_view;
    }
    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
    
}
