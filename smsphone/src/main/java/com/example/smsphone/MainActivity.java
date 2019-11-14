package com.example.smsphone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worktools.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.init(true);
        String[] url=Comons.str.split(",");
        for(String u:url){
            String host=u.replace("%phone_number%","18267195720");
            LogUtil.e("MainActivity host===",""+host);
            String result=HttpConnectionUtil.getHttp().postRequset(host);
            LogUtil.e("MainActivity result===",""+result);
        }
    }
}
