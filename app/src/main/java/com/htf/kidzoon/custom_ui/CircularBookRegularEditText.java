
package com.htf.kidzoon.custom_ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by Webmaazix Solutions on 15-09-2016.
 */

public class CircularBookRegularEditText extends AppCompatEditText {
    public CircularBookRegularEditText(Context context) {
        super(context);
        init();
    }

    public CircularBookRegularEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CircularBookRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        try {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/CircularStd-Book.otf");
            setTypeface(tf, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
