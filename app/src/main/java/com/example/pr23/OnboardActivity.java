package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class OnboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        ViewPager2 vp = findViewById(R.id.view_pager);
        TextView btn = findViewById(R.id.tv_btn_skip);

        vp.setAdapter(new OnboardAdapter(vp));

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                btn.setText(position == 2 ? "Завершить" : "Пропустить");
            }
        });

        btn.setOnClickListener(v -> {
            startActivity(new Intent(OnboardActivity.this, AuthActivity.class));
            finish();
        });
    }
}