package com.cricscore.deepakshano.cricscore.helper;

import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Deepak Shano on 1/13/2019.
 */

public class GlobalClass {
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MMM-yyyy");
    public static String Gndname="";



    public static void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
