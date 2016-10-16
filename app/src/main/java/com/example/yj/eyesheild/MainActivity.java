package com.example.yj.eyesheild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static boolean activated = false;
    static MainActivity a;
    static Intent serviceIntent;
    static int screenWidth, screenHeight;
    Button setBtn;


    public String getText() {
        if (activated) {
            return "Stop Pause Button";
        } else {
            return "Start Pause Button";
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = this;
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        setBtn = (Button) findViewById(R.id.set);
        setBtn.setText(getText());
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activated) {
                    serviceIntent = new Intent(MainActivity.this, FilterService.class);
                    startService(serviceIntent);
                    activated = true;
                    setBtn.setText(getText());
                    onBackPressed();
                } else {
                    stopService(serviceIntent);
                    activated = false;
                    setBtn.setText(getText());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FilterService.setFilterOn(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FilterService.setFilterOn(true);
    }
}
