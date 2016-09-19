package com.example.viked.paralax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParallaxView parallaxView = (ParallaxView)findViewById(R.id.paralax);
        parallaxView.setParalaxBackground(R.drawable.bg);
        parallaxView.setAdapter(new Adapter());
    }
}
