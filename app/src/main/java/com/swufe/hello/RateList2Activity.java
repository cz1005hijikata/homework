package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RateList2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "RateList2Activity";

    ListView mylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);

        //加载数据项及适配器
//        ArrayList<HashMap<String,String>> listItems=new ArrayList<HashMap<String,String>>();
//        for(int i=0;i<100;i++){
//            HashMap<String,String> map=new HashMap<String,String>();
//            map.put("itemTitle","Rate: "+i);
//            map.put("itemDetail","datail: "+i);
//            listItems.add(map);
//        }
//        SimpleAdapter listItemAdapter=new SimpleAdapter(this,
//                listItems,R.layout.list_item,
//                new String[]{"itemTitle","itemDetail"},
//                new int[]{R.id.itemTitle,R.id.itemDetail}
//        );


        mylist2=findViewById(R.id.mylist2);
        mylist2.setOnItemClickListener(this);
//       mylist2.setAdapter(listItemAdapter);

        ProgressBar bar=findViewById(R.id.progressBar);

        //处理接收回来的数据
        Handler handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==9){
                    ArrayList<HashMap<String,String>> rlist = (ArrayList<HashMap<String,String>>) msg.obj;
                    SimpleAdapter listItemAdapter=new SimpleAdapter(RateList2Activity.this,
                            rlist,R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    mylist2.setAdapter(listItemAdapter);

//                    MyAdapter myAdapter=new MyAdapter(RateList2Activity.this,
//                            R.layout.list_item,
//                            rlist
//                    );
//                    mylist2.setAdapter(myAdapter);

                    //切换显示状态
                    bar.setVisibility(View.GONE);
                    mylist2.setVisibility(View.VISIBLE);
                }
            }
        };
        RateTask2 first=new RateTask2();
        first.setHandler(handler);
        Thread t2 = new Thread(first);
        t2.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //获取ListView中点击的汇率
        Object itemAtPosition= mylist2.getItemAtPosition(position);
        HashMap<String,String> map=(HashMap<String,String>) itemAtPosition;
        String titleStr=map.get("ItemTitle");
        String detailStr=map.get("ItemDetail");
        Log.i(TAG,"onItemClick: titleStr="+titleStr);
        Log.i(TAG,"onItemClick: detailStr="+detailStr);
        //考虑通过Intent进行页面参数传递
        Intent config = new Intent(this, Ratelist3Activity.class);
        config.putExtra("ttitle", titleStr);
        config.putExtra("ddetail", detailStr);
        //打开新窗口
        startActivityForResult(config, 1);


//        TextView title=(TextView) view.findViewById(R.id.itemTitle);
//        TextView detail=(TextView) view.findViewById(R.id.itemDetail);
//        String title2=String.valueOf(title.getText());
//        String detail2=String.valueOf(detail.getText());
//        Log.i(TAG,"onItemClick: title2="+title2);
//        Log.i(TAG,"onItemClick: detail2="+detail2);

    }


}