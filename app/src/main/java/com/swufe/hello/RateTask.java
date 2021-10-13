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
import java.util.ArrayList;
import java.util.List;

public class RateTask implements Runnable {

    private static final String TAG = "MyTask";
    private Handler handler;

    public void setHandler(Handler handler){
        this.handler=handler;
    }

    //分解方法
    public void run() {
        List<String> retlist = new ArrayList<String>();
        //提取汇率内容
        try {
            Document doc= Jsoup.connect(" https://www.boc.cn/sourcedb/whpj").get();
            Log.i(TAG, "run: title=" + doc.title());
            Element firstTable=doc.getElementsByTag("tbody").get(1);;
            Elements trs=firstTable.getElementsByTag("tr");//获取行
            trs.remove(0);//去掉第一行数据
            for(Element tr: trs){//获取元素
                Log.i(TAG, "run: r=" + tr);
                Elements tds=tr.getElementsByTag("td");
                String td1=tds.get(0).text();//第一列
                String td2=tds.get(5).text();//中行折算价
                Log.i(TAG, "run: td1=" + td1+"\t td2=" + td2);
                retlist.add(td1+" -------> "+td2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送消息
        Message msg = handler.obtainMessage(9,retlist);
        handler.sendMessage(msg);
    }
}
