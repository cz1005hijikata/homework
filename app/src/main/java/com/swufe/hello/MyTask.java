package com.swufe.hello;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MyTask {

    private static final String TAG = "MyTask";
    private Handler handler;

    public void setHandler(Handler handler){
        this.handler=handler;
    }

    //分解方法
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

            //获取时间装入bundle
            Elements ps=doc.getElementsByTag("p");
//            for(Element p:ps){
//                Log.i(TAG, "run: time=" + p.text());
//            }
            Element p=ps.get(0);
            bdl.putString("time",p.text());
            Log.i(TAG,"run: time= "+p.text());

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

}
