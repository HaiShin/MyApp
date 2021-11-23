package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModelDefActivity extends AppCompatActivity {
    private String model;
    private RadioGroup radiogropu;
    private Button net_botton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_def);
        radiogropu = (RadioGroup)findViewById(R.id.radioGroup);
        net_botton =(Button) findViewById(R.id.net_done);
        net_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;i<radiogropu.getChildCount();i++){
                    RadioButton radiobn = (RadioButton) radiogropu.getChildAt(i);
                    if (radiobn.isChecked()){
                        model = radiobn.getText().toString();

                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent backIntent = new Intent(ModelDefActivity.this, MainActivity.class);
            Bundle back_bundle = new Bundle();
            back_bundle.putCharSequence("model", model);
            backIntent.putExtras(back_bundle);
            setResult(RESULT_OK, backIntent);
        }
        return super.onKeyDown(keyCode, event);
    }
}