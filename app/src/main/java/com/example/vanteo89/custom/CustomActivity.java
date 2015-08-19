package com.example.vanteo89.custom;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.vanteo89.demoparse.R;
import com.example.vanteo89.utils.TouchEffect;

/**
 * Created by vanteo89 on 14/08/2015.
 */
public class CustomActivity extends ActionBarActivity implements View.OnClickListener {
    public static final TouchEffect TOUCH=new TouchEffect();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupActionBar();
    }

    private void setupActionBar() {
        final android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar==null){
            return;
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.icon);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_actionbar));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    // set the touch Aand click listener for a view with given id
public View setTouchNClick(int id) {
    View v = setClick(id);
if(v!=null){
    v.setOnTouchListener(TOUCH);
}
    return v;
}
    public View setClick(int id){
        View v=findViewById(id);
        if(v!=null){
            v.setOnClickListener(this);
        }
        return v;
    }
    @Override
    public void onClick(View v) {

    }
}
