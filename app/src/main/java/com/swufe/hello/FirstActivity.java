package com.swufe.hello;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "FirstActivity";
    float dollar_rate = 0.35f;
    float euro_rate = 0.28f;
    float won_rate = 501f;
    Handler handler;//!!!只写定义，不写方法
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        result = findViewById(R.id.result);
        //获取文件中保存的数据
        loadFromSP();
        handler = new Handler(Looper.myLooper()) {//消息队列对象
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage:收到消息");
                if (msg.what == 6) {
                    //String str = (String) msg.obj;
                    //Log.i(TAG, "handleMessage: getMessage msg=" + str);
                    //result.setText(str);
                    Bundle bdl =(Bundle)msg.obj;
                    dollar_rate=bdl.getFloat("key_dollar");
                    euro_rate=bdl.getFloat("key_euro");
                    won_rate=bdl.getFloat("key_won");
                    Toast.makeText(FirstActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();
                    //更新
                }
                super.handleMessage(msg);
            }
        };
        //启动线程
        Thread thread = new Thread(this);
        thread.start();//这里执行run方法
    }

    private void loadFromSP() {
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //PreferenceManager.getDefaultSharedPreferences(this);
        dollar_rate = sharedPreferences.getFloat("dollar_rate", 0.11f);
        euro_rate = sharedPreferences.getFloat("euro_rate", 0.022f);
        won_rate = sharedPreferences.getFloat("won_rate", 0.33f);
    }

    public void click(View btn) {
        Log.i(TAG, "onClick: ");
        EditText rmb = findViewById(R.id.input_rmb);
        //result=findViewById(R.id.result);
        String inp = rmb.getText().toString();
        if (inp.length() > 0) {
            float num = Float.parseFloat(inp);
            float r = 0;
            if (btn.getId() == R.id.btn_dollar) {
                r = num * dollar_rate;
            } else if (btn.getId() == R.id.btn_euro) {
                r = num * euro_rate;
            } else if (btn.getId() == R.id.btn_won) {
                r = num * won_rate;
            }
            result.setText(String.valueOf(r));
        } else {
            Toast.makeText(this, "请输入金额后再进行转换", Toast.LENGTH_SHORT).show();
        }
    }

    //调转工程自身页面,数据传递
    public void open(View btn) {
        Log.i(TAG, "open: ");
        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("dollar_rate_key", dollar_rate);
        config.putExtra("euro_rate_key", euro_rate);
        config.putExtra("won_rate_key", won_rate);
        startActivityForResult(config, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //区分哪个窗口带回的数据
        //区分多个窗口带回数据的格式
        if (requestCode == 1 && resultCode == 3) {
            dollar_rate = data.getFloatExtra("dollar_key2", 0.1f);
            euro_rate = data.getFloatExtra("euro_key2", 0.1f);
            won_rate = data.getFloatExtra("won_key2", 0.1f);

        } else if (requestCode == 1 && resultCode == 5) {
            Bundle bdl = data.getExtras();
            dollar_rate = data.getFloatExtra("dollar_key3", 1);
            euro_rate = data.getFloatExtra("euro_key3", 1);
            won_rate = data.getFloatExtra("won_key3", 1);
            Log.i(TAG, "onActivityResult: dollar_rate" + dollar_rate);
            Log.i(TAG, "onActivityResult: euro_rate" + euro_rate);
            Log.i(TAG, "onActivityResult: won_rate" + won_rate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    //启用菜单项
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    //安卓不允许主线程中存在，时间开销不确定的部分
    public void run() {
        Log.i(TAG, "run:run……");
        //延迟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取网络数据
//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: html=" + html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Bundle bdl=new Bundle();
        //提取汇率内容
        try {
            Document doc = Jsoup.connect("http://www.usd-cny.com/").get();
            Log.i(TAG, "run: title=" + doc.title());
            //Elements tables=doc.getElementsByTag("table");//分析结构，考虑获取数据方式
            //Element firstTable=tables.first();
            Element firstTable=doc.getElementsByTag("table").first();
            //Log.i(TAG, "run: table=" + firstTable);//验证能找到table
//            for(Element item:firstTable.getElementsByTag("bz")){
//                Log.i(TAG, "run: item=" + item.text());
//            }
            Elements trs=firstTable.getElementsByTag("tr");//获取行
            trs.remove(0);//去掉第一行数据
            for(Element tr: trs){//获取元素
                Log.i(TAG, "run: r=" + tr);
                Elements tds=tr.getElementsByTag("td");
                //Log.i(TAG, "run: tds.count=" + tds.size());
                //tds.size=0,过滤掉title
                Element td1=tds.get(0);//第一列
                Element td2=tds.get(4);//最后一列
                Log.i(TAG, "run: td1=" + td1+"\t td2=" + td2);
                //带回一组消息
                if("美元".equals(td1.text())){//通常常量放前面
                    bdl.putFloat("key_dollar",100/Float.parseFloat(td2.text()));
                }else if("欧元".equals(td1.text())){
                    bdl.putFloat("key_euro",100/Float.parseFloat(td2.text()));
                }else if("韩币".equals(td1.text())){
                    bdl.putFloat("key_won",100/Float.parseFloat(td2.text()));
                }
            }
//            Elements ths=firstTable.getElementsByTag("th");
//            for(Element th: ths){
//                Log.i(TAG, "run: th=" + th);
//                Log.i(TAG, "run: th.html=" + th.html());//获取元素
//                Log.i(TAG, "run: th.text=" + th.text());//只保留文本
//            }
//            Element th2=ths.get(1);
//            Log.i(TAG, "run: th2=" + th2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //发送消息
        Message msg = handler.obtainMessage(6);
        //msg.what=6;
        msg.obj = bdl;
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }


}



