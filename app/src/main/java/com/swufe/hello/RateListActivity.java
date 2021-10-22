package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends AppCompatActivity {

    private static final String TAG = "RateListActivity";

    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);

        //把日期数据放在SharedPreferences里，用于保存数据库中的汇率是哪一天的数据
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);

        ListView mylist=findViewById(R.id.mylist1);
        String[] list_data={"one","two","three","four"};
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,list_data);
        mylist.setAdapter(adapter);

        //处理接收回来的数据
       handler = new Handler(Looper.myLooper()){
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

    public void run() {
        Log.i("List","run...");
        List<String> retList = new ArrayList<String>();
        Message msg = handler.obtainMessage();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);
        if(curDateStr.equals(logDate)){
            //如果相等，则不从网络中获取数据
            Log.i("run","日期相等，从数据库中获取数据");
            DBManager dbManager = new DBManager(RateListActivity.this);
            for(RateItem rateItem : dbManager.listAll()){
                retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
            }
        }else{
            Log.i("run","日期相等，从网络中获取在线数据");
            //获取网络数据
            try {
                List<RateItem> rateList = new ArrayList<RateItem>();
//                URL url = new URL("http://www.usd-cny.com/bankofchina.htm");
//                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//                InputStream in = httpConn.getInputStream();
//                String retStr = IOUtils.toString(in,"gb2312");
//                //Log.i("WWW","retStr:" + retStr);
//                //需要对获得的html字串进行解析，提取相应的汇率数据...
//                Document doc = Jsoup.parse(retStr);
                Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                Elements tables  = doc.getElementsByTag("table");

                Element retTable = tables.get(5);
                Elements tds = retTable.getElementsByTag("td");
                int tdSize = tds.size();
                for(int i=0;i<tdSize;i+=8){
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i+5);
                    //Log.i("www","td:" + td1.text() + "->" + td2.text());
                    float val = Float.parseFloat(td2.text());
                    val = 100/val;
                    retList.add(td1.text() + "->" + val);

                    RateItem rateItem = new RateItem(td1.text(),td2.text());
                    rateList.add(rateItem);
                }
                DBManager dbManager = new DBManager(RateListActivity.this);
                dbManager.deleteAll();
                Log.i("db","删除所有记录");
                dbManager.addAll(rateList);
                Log.i("db","添加新记录集");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //更新记录日期
            SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i("run","更新日期结束：" + curDateStr);
        }

        msg.obj = retList;
        //msg.what = msgWhat;
        handler.sendMessage(msg);
    }
}