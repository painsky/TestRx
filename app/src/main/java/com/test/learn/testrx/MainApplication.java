package com.test.learn.testrx;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pain on 16/8/5.
 */
public class MainApplication extends Application{
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue= Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
