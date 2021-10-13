package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Ratelist3Activity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Ratelist3Activity";
    String title;
    float detail,result=0;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratelist3);
        //通过Intent获取页面参数
        Intent intent=getIntent();
        title=intent.getStringExtra("ttitle");
        detail=Float.parseFloat(intent.getStringExtra("ddetail"));
        Log.i(TAG, "获取参数-title: "+title);
        Log.i(TAG, "获取参数-detail: "+detail);
        tv1=findViewById(R.id.tv1);
        tv1.setText(String.valueOf(title));
    }
    @Override
    public void onClick(View btn) {
        Log.i(TAG, "onClick: ");
        EditText rmb = findViewById(R.id.input);
        float inp=Float.parseFloat(rmb.getText().toString());
        Log.i(TAG, "获取参数-输入rmb: "+inp);
        result=inp * (100/detail);
        tv2=findViewById(R.id.tv2);
        tv2.setText(String.valueOf(result));
    }
}