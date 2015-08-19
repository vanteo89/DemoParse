package com.example.vanteo89.utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vanteo89 on 14/08/2015.
 */
public class TouchEffect implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//Lắng nghe khi bạn vừa nhấn xuống
            Drawable drawable = v.getBackground();
            drawable.mutate();//co the thay doi
            drawable.setAlpha(150);
            v.setBackgroundDrawable(drawable);

        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {//Lắng nghe khi bạn vừa thả nút ra
            Drawable drawable = v.getBackground();
            drawable.setAlpha(255);
            v.setBackgroundDrawable(drawable);
        }
        return false;
    }
}
