package com.swufe.hello;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity{

    private static final String TAG = "MyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activaty_rate_list);
        List<String> list1 = new ArrayList<String>();
        for(int i = 1;i<100;i++){
            list1.add("item"+i);
        }

        //adapter协调数据、控件之间的关系
//        String[] list_data={"one","two","three","four"};
//        ListAdapter adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1,list_data);
//        setListAdapter(adapter);

        //处理接收回来的数据
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==9){
                    ArrayList<String>list2 = (ArrayList<String>) msg.obj;
                    Log.i("list", "handleMessage: "+list2);
                    ListAdapter adapter = new ArrayAdapter<String>(
                            MyListActivity.this,
                            android.R.layout.simple_expandable_list_item_1,
                            list2);
                    setListAdapter(adapter);
                }
            }
        };
        RateTask rtask=new RateTask();
        rtask.setHandler(handler);
        Thread t = new Thread(rtask);
        t.start();
    }

}