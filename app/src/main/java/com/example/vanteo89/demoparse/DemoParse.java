package com.example.vanteo89.demoparse;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by vanteo89 on 13/08/2015.
 */
public class DemoParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "hawVbY5qfxjVPkfBnKnqLd8pNMlAprEVIDzg8Jza", "WV72dq6O2rdDLhXG2wFoaQSl2MS0pxnSCtTMebGc");
    }
}
