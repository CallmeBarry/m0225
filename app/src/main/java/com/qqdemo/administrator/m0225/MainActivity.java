package com.qqdemo.administrator.m0225;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "AAAAAAAAAAA";
    private TextView mTxt;
    private ImageView mImage;
    private Button mBtn_file;
    private Button mBtn_txt;
    private Button mBtn_img;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intviews();
    }

    private void intviews() {
        mTxt = (TextView) findViewById(R.id.txt);
        mImage = (ImageView) findViewById(R.id.image);
        mBtn_file = (Button) findViewById(R.id.btn_file);
        mBtn_img = (Button) findViewById(R.id.btn_img);
        mBtn_txt = (Button) findViewById(R.id.btn_txt);

        mBtn_file.setOnClickListener(this);
        mBtn_img.setOnClickListener(this);
        mBtn_txt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_file:
                mUrl = "http://www.fusioncharts.com/downloads/Evals/fusioncharts-suite-xt.zip";
                mTxt.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.GONE);
                OkHttpUtils.get().url(mUrl).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "tttt.zip") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onError: " +
                                "call:"+call+" e:"+e+" id"+id);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mTxt.setText("Total:" + total + " progress:" + progress);
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
                        mTxt.setText("下载完成");
                    }
                });
                break;

            case R.id.btn_txt:
                mTxt.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.GONE);
                mUrl = "http://www.baidu.com";
                OkHttpUtils.get().url(mUrl).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mTxt.setText(response + " id:" + id);
                    }
                });
                break;
            case R.id.btn_img:
                mTxt.setVisibility(View.GONE);
                mImage.setVisibility(View.VISIBLE);
                mUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
                OkHttpUtils.get().url(mUrl).build().execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        mImage.setImageBitmap(response);
                    }
                });
                break;

        }
    }
}
