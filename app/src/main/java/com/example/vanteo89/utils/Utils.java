package com.example.vanteo89.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by vanteo89 on 14/08/2015.
 */
public class Utils {
    public static final void hideKeyboard(Activity ctx){
        if(ctx.getCurrentFocus()!=null){
            InputMethodManager imn= (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(),0);
        }
    }

    public static final void hideKeyboard(Activity ctx,View v){
        try {
            InputMethodManager imn= (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(v.getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
