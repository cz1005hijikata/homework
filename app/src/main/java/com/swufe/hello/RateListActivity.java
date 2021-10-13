package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RateListActivity extends AppCompatActivity {

    private static final String TAG = "RateListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        ListView mylist=findViewById(R.id.mylist1);
        String[] list_data={"one","two","three","four"};
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,list_data);
        mylist.setAdapter(adapter);

        //处理接收回来的数据
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==9){
                    ArrayList<String> list2 = (ArrayList<String>) msg.obj;
                    Log.i("list", "handleMessage: "+list2);
                    ListAdapter adapter = new ArrayAdapter<String>(
                            RateListActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            list2);
                    mylist.setAdapter(adapter);
                }else if(msg.what==6){
                    Bundle bd=(Bundle) msg.obj;
                    //Log.i("handlemessage:bundle", +bd.getFloat(key_dollar));
                }
            }
        };

        RateTask first=new RateTask();
        first.setHandler(handler);
        Thread t2 = new Thread(first);
        t2.start();
    }
}