package com.pathteam.hikeitv2.Stages;


        import android.app.Application;

        import com.pathteam.hikeitv2.HikeApplication;
        import com.pathteam.hikeitv2.R;
        import com.pathteam.hikeitv2.Riggers.SlideRigger;
/**
 * Created by nicholashall on 11/16/16.
 */
public class MainMenuStage extends IndexedStage {
    private final SlideRigger rigger;
    public MainMenuStage(Application context){
        super(MainMenuStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public MainMenuStage(){
        this(HikeApplication.getInstance());
    }
    @Override
    public int getLayoutId() {
        return R.layout.main_menu_view;
    }
    @Override
    public Rigger getRigger() {
        return rigger;
    }
}