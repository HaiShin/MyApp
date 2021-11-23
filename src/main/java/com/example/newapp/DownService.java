package com.example.newapp;

import static android.content.ContentValues.TAG;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DownService {

    public static void downLoad(String train_path, String test_path, Context context)throws Exception
    {
        URL train_url = new URL(train_path);
        URL test_url = new URL(test_path);
        InputStream is = train_url.openStream();
        InputStream is_test = test_url.openStream();
        String end_train = train_path.substring(train_path.lastIndexOf("."));
        String end_test = test_path.substring(test_path.lastIndexOf("."));

        OutputStream os_train = context.openFileOutput("Cache_"+System.currentTimeMillis()+end_train, Context.MODE_PRIVATE);
        Log.i(TAG, "downLoad: "+os_train);
        OutputStream os_test = context.openFileOutput("Cache_"+System.currentTimeMillis()+end_test, Context.MODE_PRIVATE);
        byte[] buffer = new byte[1024];
        byte[] buffer1 = new byte[1024];
        int len = 0;

        while((len = is.read(buffer)) > 0)
        {
            os_train.write(buffer,0,len);
        }
        is.close();
        os_train.close();
        len = 0;
        while((len = is_test.read(buffer1)) > 0)
        {
            os_test.write(buffer,0,len);
        }
        is_test.close();
        os_test.close();

    }

}
