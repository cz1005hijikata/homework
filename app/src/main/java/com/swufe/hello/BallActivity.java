package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BallActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG="BallActivity";
    int score1=0;
    int score2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
    }

    @Override
    public void onClick(View btn) {
        Log.i(TAG,"click:");
        if(btn.getId()==R.id.button9){
            score1+=1;
        }else if(btn.getId()==R.id.button8){
            score1+=2;
        }else if(btn.getId()==R.id.button7){
            score1+=3;
        }else{
            score1=0;
            score2=0;
        }
        showScore();
    }
    private void showScore(){
        TextView show1=findViewById(R.id.textView6);;
        show1.setText(""+score1);
        TextView show2=findViewById(R.id.textView6b);;
        show2.setText(""+score2);
    }

    public void onClickb(View btn) {
        Log.i(TAG,"click:");
        if(btn.getId()==R.id.button9b){
            score2+=1;
        }else if(btn.getId()==R.id.button8b){
            score2+=2;
        }else if(btn.getId()==R.id.button7b){
            score2+=3;
        }else{
            score2=0;
            score1=0;
        }
        showScore();
    }
}

//    Button b1,b2,b3,b4;
//    int sum;
//        b1=findViewById(R.id.button9);
//        b2=findViewById(R.id.button8);
//        b3=findViewById(R.id.button7);
//        b4=findViewById(R.id.button10);
//        tt=findViewById(R.id.textView6);
//btn.getID()==R.id.btn3
//        b1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                sum=Integer.parseInt(tt.getText().toString());
//                sum+=1;
//                tt.setText(""+sum);
//            }
//        });
//        b2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                sum=Integer.parseInt(tt.getText().toString());
//                sum+=2;
//                tt.setText(""+sum);
//            }
//        });
//        b3.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                sum=Integer.parseInt(tt.getText().toString());
//                sum+=3;
//                tt.setText(""+sum);
//            }
//        });
//        b4.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                sum=Integer.parseInt(tt.getText().toString());
//                sum=0;
//                tt.setText(""+sum);
//            }
//        });