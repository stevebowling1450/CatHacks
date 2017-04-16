package com.pathteam.hikeitv2.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pathteam.hikeitv2.Components.Constants;
import com.pathteam.hikeitv2.Components.Utils;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.MainActivity;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Stages.DifficultyDetailsStage;
import com.pathteam.hikeitv2.Stages.MainMenuStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;

/**
 * Created by JoshuaMabry on 11/18/16.
 */

public class CaloriesBurnedView extends LinearLayout {

    private Context context;
    Double MET;
    Double totalCaloriesBurned;
    Integer totalTimeInHours=0;
    Integer totalTimeInMinutes=0;
    Double totalWeight;

    @Bind(R.id.selectedWeight)
    EditText selectedWeight;

    @Bind(R.id.lightButton)
    RadioButton lightButton;

    @Bind(R.id.moderateButton)
    RadioButton moderateButton;

    @Bind(R.id.intenseButton)
    RadioButton intenseButton;

    @Bind(R.id.displayCaloriesBurned)
    TextView displayCaloriesBurned;

    @Bind(R.id.calculateButton)
    Button calculateButton;

    @Bind(R.id.getDetailsButton)
    Button getDetailsButton;

    @Bind(R.id.selectedMinutes)
    TextView selectedMinutes;

    @Bind(R.id.selectedHours)
    TextView selectedHours;






    public CaloriesBurnedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        String min = Long.toString(Constants.timeMin);
        String hour = Long.toString(Constants.timeHour) ;
        selectedMinutes.setText(min);
        selectedHours.setText(hour);
    }

    private Double calculateCaloriesBurned() {

        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(selectedWeight.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(selectedHours.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(selectedMinutes.getWindowToken(), 0);

        if (lightButton.isChecked()) {
            MET = 2.3;
        }
        if (moderateButton.isChecked()) {
            MET = 3.6;
        }
        if (intenseButton.isChecked()) {
            MET = 5.3;
        }

        String timeInMinutes = selectedMinutes.getText().toString();
        if(timeInMinutes.isEmpty()){
            totalTimeInMinutes = 0;
        } else {
            totalTimeInMinutes = Integer.parseInt(timeInMinutes);
        }

        String timeInHours = selectedHours.getText().toString();
        if(timeInHours.isEmpty()){
            totalTimeInHours = 0;
        } else {
            totalTimeInHours = Integer.parseInt(timeInHours);
        }

        String weight = selectedWeight.getText().toString();
         totalWeight = Double.parseDouble(weight);

        totalCaloriesBurned = (((totalTimeInHours*60)+totalTimeInMinutes) *(MET *(totalWeight/2.2)))/200;
        totalCaloriesBurned = Math.round(totalCaloriesBurned *100.0)/100.0;
        return totalCaloriesBurned;
    }
    @OnClick(R.id.calculateButton)
    public void Calculate(){

        try {
            calculateCaloriesBurned();
        } catch (Exception e) {
            e.printStackTrace();
            totalCaloriesBurned = 0.0;
        }
        String caloriesBurned = totalCaloriesBurned.toString();
            displayCaloriesBurned.setText(caloriesBurned);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera);

        try {
            ((MainActivity) getContext()).hikelist.add(new HikeList(Constants.title + "\n" + "Distance: " + Constants.distance + "\n" + Constants.hikeTime, Constants.markersArray, Constants.note, Utils.encodeTobase64(Constants.me),totalCaloriesBurned.toString()));
        } catch (Exception e) {
            ((MainActivity) getContext()).hikelist.add(new HikeList(Constants.title + "\n" + "Distance: " + Constants.distance + "\n" + Constants.hikeTime, Constants.markersArray, Constants.note, Utils.encodeTobase64(bitmap),totalCaloriesBurned.toString()));
            Toast.makeText(context, "You Did Not Choose an Image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        ((MainActivity) getContext()).writeHikes();
        Flow flow = HikeApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new MainMenuStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }


    @OnClick(R.id.getDetailsButton)
    public void getDetails() {
        Flow flow = HikeApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new DifficultyDetailsStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }
}
