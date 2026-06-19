package com.example.pr23;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class DeliverySuccessActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_success);

        ProgressBar progress =
                findViewById(R.id.progress);

        ObjectAnimator animation =
                ObjectAnimator.ofInt(
                        progress,
                        "progress",
                        0,
                        100);

        animation.setDuration(2000);
        animation.start();
    }
}