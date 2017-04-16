package com.pathteam.hikeitv2.Stages;

import android.app.Application;

import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Riggers.SlideRigger;


/**
 * Created by stevebowling on 10/31/16.
 */

public class HikeItMapStage extends IndexedStage {
    private final SlideRigger rigger;

    public HikeItMapStage(Application context){
        super(HikeItMapStage.class.getName());
        this.rigger=new SlideRigger(context);
    }

    public HikeItMapStage(){
        this(HikeApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.maps_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
