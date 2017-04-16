package com.pathteam.hikeitv2;

import android.app.Application;

import com.pathteam.hikeitv2.Stages.HikeItMapStage;
import com.pathteam.hikeitv2.Stages.MainMenuStage;

import flow.Flow;
import flow.History;

/**
 * Created by stevebowling on 10/31/16.
 */

public class HikeApplication extends Application {
    private static HikeApplication application;
    public final Flow mainFlow=
            new Flow(History.single(new MainMenuStage()));


    @Override
    public void onCreate() {
        super.onCreate();

        application=this;
    }
    public static HikeApplication getInstance(){
        return application;
    }
    public static Flow getMainFlow(){
        return getInstance().mainFlow;
    }
}
