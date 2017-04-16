package com.pathteam.hikeitv2.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by JoshuaMabry on 11/17/16.
 */

public class MyCustomTextViewRubiksBlack extends android.support.v7.widget.AppCompatTextView {
    public MyCustomTextViewRubiksBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/Rubik-Black.ttf"));
    }
}
