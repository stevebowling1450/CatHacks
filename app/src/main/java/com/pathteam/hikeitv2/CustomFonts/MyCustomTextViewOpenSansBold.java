package com.pathteam.hikeitv2.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by JoshuaMabry on 11/28/16.
 */

public class MyCustomTextViewOpenSansBold extends TextView {
    public MyCustomTextViewOpenSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/OpenSans-Bold.ttf"));
    }

}
