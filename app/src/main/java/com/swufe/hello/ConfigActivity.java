package com.swufe.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigActivity extends AppCompatActivity implements View.OnClickListener{

    float dollar2,euro2,won2;
    EditText dollarText,euroText,wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent=getIntent();
        dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
        euro2=intent.getFloatExtra("euro_rate_key",0.0f);
        won2=intent.getFloatExtra("won_rate_key",0.0f);
        dollarText=findViewById(R.id.dollar_show);
        euroText=findViewById(R.id.euro_show);
        wonText=findViewById(R.id.won_show);
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }

    @Override
    public void onClick(View btn) {
        float newDollar=Float.parseFloat(dollarText.getText().toString());
        float newEuro=Float.parseFloat(euroText.getText().toString());
        float newWon=Float.parseFloat(wonText.getText().toString());

        //获取当前对象
        Intent first=getIntent();

        Bundle bdl=new Bundle();
        bdl.putFloat("dollar_key3",newDollar);
        bdl.putFloat("euro_key3",newEuro);
        bdl.putFloat("won_key3",newWon);
        first.putExtras(bdl);
        setResult(5,first);
//        first.putExtra("dollar_key2",newDollar);
//        first.putExtra("euro_key2",newEuro);
//        first.putExtra("won_key2",newWon);
//        setResult(3,first);

        //结束当前窗口，不会不断产生新的窗口
        finish();
    }
}