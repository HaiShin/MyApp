package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newapp.Train.trainRPS;
import com.example.newapp.Train.DownLoadDataset;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.IOException;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int UPDATE_IMG = 1;
    private static MainActivity instance;
    private Button data_load_bn;
    private Button model_def_bn;
    private Button down_data;
    private TextView pre_train_model;
    private Button train_bn;
    private static TextView acc;
    private static TextView loss;
    private static TextView epoch;
    private Button fed_activity;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private EditText unit_num;
    private EditText iter;
    private EditText learning_rate;
    private EditText batch_size;
    private EditText class_nums;
    private Spinner model_spinner;
    private String model;
    public Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_IMG:
                    System.out.println(DownLoadDataset.dataDir);
                    System.out.println(DownLoadDataset.filename);
                    String path=DownLoadDataset.dataDir +"/rps_64"; ///data/user/0/com.example.newapp/cache
                    System.out.println(path);
                    String path1 = path + "/paper/paper01-000.png";
                    String path2 = path + "/rock/rock01-000.png";
                    String path3 = path + "/scissors/scissors01-000.png";
                    String path4 = path + "/paper/paper01-001.png";
                    String path5 = path + "/rock/rock01-001.png";
                    Drawable img1 = BitmapDrawable.createFromPath(path1);
                    Drawable img2 = BitmapDrawable.createFromPath(path2);
                    Drawable img3 = BitmapDrawable.createFromPath(path3);
                    Drawable img4 = BitmapDrawable.createFromPath(path4);
                    Drawable img5 = BitmapDrawable.createFromPath(path5);
                    image1.setImageDrawable(img1);
                    image2.setImageDrawable(img2);
                    image3.setImageDrawable(img3);
                    image4.setImageDrawable(img4);
                    image5.setImageDrawable(img5);
                    image6.setImageDrawable(img1);

                    break;
                default:
                    break;

            }
        }
    };
    public static Handler handler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            double acc_d = bundle.getDouble("acc");
            double loss_d = bundle.getDouble("loss");
            Integer epoch_d = bundle.getInt("epoch");
            acc.setText(String.format("%.2f",acc_d));
            loss.setText(String.format("%.2f",loss_d));
            epoch.setText(epoch_d+"");

        }
    };
    public static MainActivity getInstance(){
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        data_load_bn = findViewById(R.id.dl_bn);
        model_def_bn = findViewById(R.id.model_def_bn);
        down_data = findViewById(R.id.down_data);
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        image4 = findViewById(R.id.imageView4);
        image5 = findViewById(R.id.imageView5);
        image6 = findViewById(R.id.imageView6);
        train_bn = findViewById(R.id.train_bn);
        acc = findViewById(R.id.acc);
        loss = findViewById(R.id.loss);
        epoch = findViewById(R.id.epoch_num);
        model_spinner = findViewById(R.id.spinner_train_model);
        unit_num = findViewById(R.id.unit_num);
        iter = findViewById(R.id.epoch);
        learning_rate = findViewById(R.id.Learning_Rate);
        batch_size = findViewById(R.id.batch);
        class_nums = findViewById(R.id.class_nums);
        fed_activity = findViewById(R.id.fed_activity);
        fed_activity.setOnClickListener(this);
        data_load_bn.setOnClickListener(this);
        model_def_bn.setOnClickListener(this);
        down_data.setOnClickListener(this);
        train_bn.setOnClickListener(this);
        model_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model = (String) model_spinner.getSelectedItem();
                if (!model.equals("Default Model")){
                    Toast.makeText(MainActivity.this,model,Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dl_bn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_IMG;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.model_def_bn:
                Intent intent = new Intent(MainActivity.this, ModelDefActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.fed_activity:
                Intent intent_fed = new Intent(MainActivity.this,FedActivity.class);
                startActivityForResult(intent_fed,3);
                break;
            case R.id.down_data:
                new Thread(()-> {
                    try {
                        String TraindataURL="http://112.124.109.236/rps_64.zip";
                        String[] filenamelist=TraindataURL.split("/");
                        String filename=filenamelist[filenamelist.length-1];
                        DownLoadDataset.download(TraindataURL,filename);
                        Context context = getApplicationContext();
                        Looper.prepare();
                        Toast.makeText(context, "数据已下载完成", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case R.id.train_bn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        trainRPS train = new trainRPS();
                        //得到一些参数
                        int unit = Integer.parseInt(unit_num.getText().toString());
                        int iteration = Integer.parseInt(iter.getText().toString());
                        Float lr = Float.parseFloat(learning_rate.getText().toString());
                        int bs = Integer.parseInt(batch_size.getText().toString());
                        int cn = Integer.parseInt(class_nums.getText().toString());
                        try {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this,"unit="+unit+",iter="+iteration+",lr="+lr+",bs="+bs+",class nums="+cn,Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            train.train();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pre_train_model = (TextView)findViewById(R.id.model_def_bn);
        if (data != null){
            String model = data.getStringExtra("model");
            if (requestCode == 2 && resultCode == RESULT_OK ){
                pre_train_model.setText(model);
            }
        }

    }
}