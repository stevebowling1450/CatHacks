package com.pathteam.hikeitv2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pathteam.hikeitv2.Components.Constants;
import com.pathteam.hikeitv2.Components.Utils;
import com.pathteam.hikeitv2.Model.HikeList;
import com.pathteam.hikeitv2.Model.hMarker;
import com.pathteam.hikeitv2.Stages.HikeItMapStage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {




    public static Integer position;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static final int RESULT_LOAD_IMAGE=1;

    String filename = "HikeHistoryFile";
    Gson gson = new Gson();
    public List<HikeList>  hikelist = new ArrayList<>();
    private ArrayList<Object>allItems = new ArrayList<>();
    // these are the basic lat longs that are used to set up first run save file. We don't want an empty
    LatLng setup1 = new LatLng(37.816,-82.809);
    LatLng setup2 = new LatLng(37.818,-82.810);
    LatLng setup3 = new LatLng(37.820,-82.811);
    LatLng setup4 = new LatLng(37.821,-82.811);

   // private ArrayList<mMarker> hikeArray;
    public ArrayList <hMarker> hike = new ArrayList<>();

    private Flow flow;
    private ScreenplayDispatcher dispatcher;

    @Bind(R.id.container)
    RelativeLayout container;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        flow = HikeApplication.getMainFlow();
        dispatcher=new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);
//Get ready for our json save file.
        gson = new Gson();
        // lets go get our saved hikes!
        setupHikes();


        if (Build.VERSION.SDK_INT >= 23){
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 23){
            if (!(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            if (!(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }



    }


    // This sets up the inital list of hikes if there are none that the user has made.
    // This lets us get started. Also it check if the save file exists and if so read that info.
    private void setupHikes(){


        File filesDir = this.getFilesDir();
        File hikeFile = new File(filesDir + File.separator + filename);

        if (hikeFile.exists()) {
            readHikes(hikeFile);
        }else{
            // set up new hike info we will start with 3 hikes and each will have a set of markers.
            hikelist.add(new HikeList("Paintsville Lake Trail", new ArrayList<hMarker>(),"A trail surrounding the lake. Easy to Medium Difficulty level. Some more text to fill space....","IMAGE",""));
            hikelist.add(new HikeList("Hidden Trail Behind Lake", new ArrayList<hMarker>(),"A trail surrounding the lake. Easy to Medium Difficulty level. Some more text to fill space....","IMAGE",""));
            hikelist.add(new HikeList("Nice Calm Hike", new ArrayList<hMarker>(),"A trail surrounding the lake. Easy to Medium Difficulty level. Some more text to fill space....","IMAGE",""));
//this adds a few points to each of our hikes in our list.
//this adds a few points to each of our hikes in our list.

//this adds a few points to each of our hikes in our list.

            //this adds a few points to each of our hikes in our list.

            hikelist.get(0).hmarker.add(new hMarker(1,setup1,new Date()));
            hikelist.get(0).hmarker.add(new hMarker(2,setup4,new Date()));
            hikelist.get(1).hmarker.add(new hMarker(1,setup2,new Date()));
            hikelist.get(2).hmarker.add(new hMarker(1,setup3,new Date()));

            writeHikes();

        }
    }

    // app starts and reads the json file to fill the arrays with sweet sweet data.

    private void readHikes(File todoFile) {
        FileInputStream inputStream = null;
        String hikesText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {}
            hikesText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            Type collectionType = new TypeToken<List<HikeList>>(){}.getType();
            List<HikeList> categoryList = gson.fromJson(hikesText, collectionType);
            hikelist = new LinkedList(categoryList);

            updateAllItems();
            testArrays();
        }
    }
// this writes the json file for saving.  and we will call this method each time we end or save a hike.

    public void writeHikes() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(hikelist);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {}
        }
    }

    // we may not need this.  mark it for deletion

    private void updateAllItems() {
        allItems.clear();
        for(int i = 0; i < hikelist.size(); i++) {
            allItems.add(hikelist.get(i).getTitle());
            for(int j = 0; j < hikelist.get(i).hmarker.size(); j++) {
                allItems.add(hikelist.get(i).hmarker.get(j));
            }
        }
    }

    // This is how we will get the info from the arrays. we can replace the testArrays method with a production one.
    private void testArrays(){
        Integer listsize = hikelist.size();

        for (int i = 0; i < listsize; i++){
            Log.d("!!!!!", hikelist.get(i).getTitle());
            Log.d("!!!!!", hikelist.get(i).getHikeNotes());
            Log.d("!!!!!", hikelist.get(i).hmarker.get(0).getDate().toString());
            for (int x = 0; x < hikelist.get(i).hmarker.size(); x++){
                Log.d("****", hikelist.get(i).hmarker.get(x).getMarkerId().toString());
                Log.d("****", hikelist.get(i).hmarker.get(x).getDate().toString());
                Log.d("****", hikelist.get(i).hmarker.get(x).getMarkerPos().toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!flow.goBack()){
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new HikeItMapStage()),
                    Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

    // This is used to use the camera

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        //alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(MainActivity.this);
                    }
                });

       // alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                           // showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(MainActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    // This is used to get pic from the gallery

    public void getImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.galleryPicture);
            Bitmap image = BitmapFactory.decodeFile(picturePath);
            Bitmap smallImg = Utils.resize(image);
            Utils.encodeTobase64(smallImg);
            imageView.setImageBitmap(smallImg);
            Constants.me= smallImg;

        }
    }
}
