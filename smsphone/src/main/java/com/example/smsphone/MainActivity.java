package com.example.smsphone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worktools.util.Logx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logx.init(true);
        String[] url=Comons.str.split(",");
        for(String u:url){
            String host=u.replace("%phone_number%","18267195720");
            Logx.e("MainActivity host===",""+host);
            String result=HttpConnectionUtil.getHttp().postRequset(host);
            Logx.e("MainActivity result===",""+result);
        }
    }
}
