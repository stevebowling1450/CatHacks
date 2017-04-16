package com.pathteam.hikeitv2.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Riggers.SlideRigger;
/**
 * Created by nicholashall on 11/17/16.
 */
public class HikeDetailStage extends IndexedStage {
    private final SlideRigger rigger;
    public HikeDetailStage(Application context){
        super(HikeListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public HikeDetailStage(){
        this(HikeApplication.getInstance());
    }
    @Override
    public int getLayoutId() {
        return R.layout.hike_detail_view;
    }
    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}



