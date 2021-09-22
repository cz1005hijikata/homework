package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final String TAG = "MainActivity";
    //TextView showout;定义类变量
    //出现闪退后，找报错NullPointException
    //局部变量与类变量同名时，局部变量优先

    EditText hh,ww;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        hh = findViewById(R.id.height);
        ww = findViewById(R.id.weight);
        Button btn1 = findViewById(R.id.btn);
        btn1.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //获取用户输入
        Double h1= Double.parseDouble(hh.getText().toString());
        Double w1= Double.parseDouble(ww.getText().toString());
        Double BMI=w1/(h1*h1);
        String suggest;
        if (BMI < 18.5){
            suggest="偏瘦，请增加营养，不能挑食噢！";
        }else if (BMI >= 18.5 && BMI < 25 ){
            suggest="正常，要继续保持噢！";
        }else if (BMI >= 25 && BMI < 30 ) {
            suggest="超重，请适度锻炼，少吃油炸食品噢！";
        }else if (BMI >= 30 && BMI < 35 ) {
            suggest="轻度肥胖，请适度锻炼，注意健康饮食和作息习惯噢！";
        }
        else if (BMI >= 35 && BMI < 40 ) {
            suggest="中度肥胖，请加强锻炼，为了健康的身体，注意健康饮食和作息习惯噢！";
        }
        else {
            suggest="重度肥胖，请务必有计划的锻炼，为了健康的身体，可以寻求专业教练的帮助，不要灰心噢！";
        }
        TextView r1 = findViewById(R.id.result);
        r1.setText("您的体重指数是："+String.format("%.2f", BMI)+"\n健康建议："+suggest);
    }
}



//    TextInputEditText aa;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.change);
//
//        aa = findViewById(R.id.textInputEditText);
//
//        Button btn = findViewById(R.id.button6);
//        btn.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        //获取用户输入
//        Double d1= Double.parseDouble(aa.getText().toString());
//        Double d2=d1*1.8+32;
//        TextView bb = findViewById(R.id.textView5);
//
//        //*1.8+32
//        bb.setText(d2.toString());
//    }
//}

//        TextView aa=findViewById(R.id.textView2);
//        //String car=aa.getText().toString();
//
//        //获取系统时间并显示
//        Calendar now = Calendar.getInstance();
//        //aa.setText(now.getTime().toString());
//
//        //系统日志
//        //Log.i(TAG,"onCreate: "+now.getTime());
//
//        Button btn=findViewById(R.id.button17);
////        btn.setOnClickListener(new View.OnClickListener(){
////            public void onClick(View v) {
////                Log.i(TAG,"onClick: aaaaa");
////            }
////        });
//        btn.setOnClickListener(this);//python 当前类自身
//        //系统自动调用 this.onClick方法
//    }
//
//    @Override
//    public void onClick(View view) {
//        Log.i(TAG,"onClick: aaaaa");
//        //获取用户输入
//        TextView aa=findViewById(R.id.textView2);
//        String car=aa.getText().toString();
//
//        TextView aaa=findViewById(R.id.textView);
//        aaa.setText("Hi~"+aa);
//    }
//}