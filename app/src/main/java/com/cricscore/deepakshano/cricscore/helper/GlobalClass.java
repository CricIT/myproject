package com.cricscore.deepakshano.cricscore.helper;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by Deepak Shano on 1/13/2019.
 */

public class GlobalClass {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor edit;
    public static String mobileNumber="";
    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat outputdateformat = new SimpleDateFormat("dd-MMM-yyyy");
    public static String Gndname="";
    public static String usertoken="";
    public static String[] ageGroup = {"Under 12", "Under 14", "Under 16", "Under 19", "Under 23", "23+"};
    public static String[] school = {"Under 12", "Under 14", "Under 16"};
    public static String[] collage = {"Under 19", "Under 23"};
    public static String[] corparate = { "Under 23", "23+"};
    public static String[] generalgroup = {"School", "College", "Corporate"};
    public static  String[] schoolgroup = {"School"};
    public static String[] collegegroup = {"College"};
    public static String[] intermediategroup = {"College","Corporate"};

    public static void showView(View... views) {
        try{
            for (View v : views) {
                v.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void hideView(View... views) {
        try{
            for (View v : views) {
                v.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 10) {
                // if(phone.length() != 10) {
              check=false;
            } else {
                check = true;
            }
        }
        return check;
    }

    public static void hideKeyboard(InputMethodManager inputManager, View focusedView) {
        try {
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
            if (focusedView != null) {
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
