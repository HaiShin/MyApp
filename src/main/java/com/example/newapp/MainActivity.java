package com.example.newapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button data_load_bn;
    private Button model_def_bn;
    private TextView pre_train_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data_load_bn = (Button) findViewById(R.id.dl_bn);
        model_def_bn = (Button)findViewById(R.id.model_def_bn);
        data_load_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataLoadActivitty.class);
                startActivityForResult(intent, 1); //标记mainActivity的标识码为1
            }
        });
        model_def_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModelDefActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pre_train_model = (TextView)findViewById(R.id.pre_train_model);
        if (data != null){
            String model = data.getStringExtra("model");
            if (requestCode == 2 && resultCode == RESULT_OK ){
                pre_train_model.setText(model);
            }
        }

    }
}