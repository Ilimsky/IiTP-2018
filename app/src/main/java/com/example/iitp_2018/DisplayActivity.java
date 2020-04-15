package com.example.iitp_2018;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        textView = (TextView) findViewById(R.id.text_view_answers);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(getIntent().getStringExtra("text_id"));
    }
}
