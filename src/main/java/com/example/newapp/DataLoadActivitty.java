package com.example.newapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.MediaType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DataLoadActivitty extends AppCompatActivity {
    private Button button, shared;
    private TextView textView;
    private String path = "";
    private Uri uri;
    private TextView textView8;
    private Button label0_bn;


    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_load);
        Button image_bn = (Button) findViewById(R.id.image_file);
        textView = findViewById(R.id.textView3);
        textView8 = findViewById(R.id.textView8);
        image_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开系统的文件选择器
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //onActivityResult(1,1,intent);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    // 获取文件的真实路径
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        Uri uri = data.getData(); // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            String path = uri.getPath();
            return;
        }
        if (cursor.moveToFirst()) {
            // 多媒体文件，从数据库中获取文件的真实路径
            String path = cursor.getString(cursor.getColumnIndex("_data"));
        }
        Log.i(TAG, "onActivityResult: " + path);
        cursor.close();
    }
}
