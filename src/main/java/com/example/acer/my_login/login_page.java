package com.example.acer.my_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class login_page extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        tv=(TextView)findViewById(R.id.tv);
//        Intent i=getIntent();
//        Bundle b=i.getExtras();
//        String name=b.getString("k1");
//        long num=b.getLong("k2");
//        String add=b.getString("k3");
//        String email=b.getString("k4");
//        String pass=b.getString("k5");
        //tv.setText("Name is: "+name+"\n"+"Mobile number is: "+num+"\n"+"Address is: "+add+"\n"+"Email Address is: "+email+"\n"+"Password is: "+pass+"\n");
        tv.setText("Welcome  User");
    }
}
