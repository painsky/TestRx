package com.test.learn.testrx;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String url="http://c.hiphotos.baidu.com/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=c0c39c0ab87eca80060831b5f04afcb8/d4628535e5dde711e23d79dfafefce1b9d16612f.jpg";
    private Bitmap bitmap=null;
    private Drawable drawable;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.getRequestQueue().cancelAll("im_request");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.img);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dosomthing();
            }
        });
    }

    private void dosomthing() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                getImg();
                subscriber.onNext(drawable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
                Log.e("flag", "call: what" );

            }
        });
    }
    private void getImg(){
        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                drawable=new BitmapDrawable(response);
                imageView.setImageDrawable(drawable);
                Toast.makeText(MainActivity.this, "图片获取成功"+response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        imageRequest.setTag("im_request");
        MainApplication.getRequestQueue().add(imageRequest);
    }
}
