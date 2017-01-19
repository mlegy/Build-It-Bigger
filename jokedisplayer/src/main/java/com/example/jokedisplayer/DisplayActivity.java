package com.example.jokedisplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView jokeTextView = (TextView) findViewById(R.id.jokeTextView);
        jokeTextView.setText(getIntent().getStringExtra("joke"));
    }
}
