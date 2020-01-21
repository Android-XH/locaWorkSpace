package com.example.wechathoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wechathoke.hoke.WeChatHook;
import com.example.wechathoke.hoke.xposed.WechatUtils;
import com.example.wechathoke.observer.EventCar;
import com.example.wechathoke.observer.ObData;

import static com.example.wechathoke.observer.Status.SEND;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText=findViewById(R.id.et_content);

        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=editText.getText().toString();
                if(!content.isEmpty()){
//                    WechatUtils.sendTextMessage(WeChatHook.getLoadPackageParam().classLoader,content,"");
                }else{
                    Toast.makeText(MainActivity.this,"请输入内容！",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
