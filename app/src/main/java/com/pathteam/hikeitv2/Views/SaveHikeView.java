package com.pathteam.hikeitv2.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pathteam.hikeitv2.Components.Constants;
import com.pathteam.hikeitv2.HikeApplication;
import com.pathteam.hikeitv2.MainActivity;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.R;
import com.pathteam.hikeitv2.Stages.CaloriesBurnedStage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;


/**
 * Created by jeremiahlewis on 11/18/16.
 */

public class SaveHikeView extends RelativeLayout {

    private Context context;

    public ArrayList <HikeList> currentHike = new ArrayList<>();


    @Bind(R.id.saveButton)
    Button saveButton;

    @Bind(R.id.pick_photo_button)
    Button choosePhoto;

    @Bind(R.id.hike_title)
    EditText hikeTitle;

    @Bind(R.id.hike_notes)
    EditText hikeNotes;

    @Bind(R.id.galleryPicture)
    ImageView galleryPicture;

    String timeInMin;
    String timeInSec;




    // formats date to just hours and min.
    java.util.Date date = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");




    public SaveHikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    @OnClick(R.id.pick_photo_button)
    public void getPic() {
        ((MainActivity) getContext()).getImage();

    }
    @OnClick(R.id.saveButton)
    public void save() {
        EditText title = (EditText) findViewById(R.id.hike_title);
        EditText note = (EditText) findViewById(R.id.hike_notes);
        if (!title.getText().toString().equals("") && !note.getText().toString().equals("")) {
            Date startDate = Constants.markersArray.get(0).getDate();
            int i = Constants.markersArray.size() - 1;
            Date endDate = Constants.markersArray.get(i).getDate();
            String time = "";
            try {
                Date d1 = startDate;
                Date d2 = endDate;

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                Constants.timeMin = diffMinutes;
                Constants.timeHour = diffHours;
                int TimeSec = (int) diffSeconds;
                int TimeMin = (int) diffMinutes;

                if (TimeSec < 10) {
                    timeInSec = "0" + String.valueOf(TimeSec);
                } else {
                    timeInSec = String.valueOf(TimeSec);
                }

                if (TimeMin < 10) {
                    timeInMin = "0" + String.valueOf(TimeMin);
                } else {
                    timeInMin = String.valueOf(TimeMin);
                }


                time = "Duration: " + String.valueOf(diffHours) + ":" + timeInMin + ":" + timeInSec;

            } catch (Exception e) {
                e.printStackTrace();
            }


            Constants.title = title.getText().toString();
            Constants.hikeTime = time;
            Constants.note = note.getText().toString();
            ((MainActivity) getContext()).writeHikes();
            Flow flow = HikeApplication.getMainFlow();
            flow.setHistory(History.single(new CaloriesBurnedStage()),
                    Flow.Direction.BACKWARD);

        }else {
            Toast.makeText(context, "Please Complete All Fields", Toast.LENGTH_SHORT).show();
        }
    }

    }


