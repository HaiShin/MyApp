package com.example.newapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button data_load_bn;
    private Button model_def_bn;
    private TextView pre_train_model;
    private Button down_minist;
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg){
            if (msg.what == 0x123){
                Toast.makeText(getApplicationContext(), "正在下载", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data_load_bn = (Button) findViewById(R.id.dl_bn);
        model_def_bn = (Button)findViewById(R.id.model_def_bn);
        down_minist = (Button)findViewById(R.id.down_mnist);
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
        down_minist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        String train_path = "https://pjreddie.com/media/files/mnist_train.csv";
                        String test_path = "https://pjreddie.com/media/files/mnist_test.csv";
                        try{
                            DownService.downLoad(train_path, test_path, MainActivity.this);
                        }catch (Exception e){e.printStackTrace();}
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
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