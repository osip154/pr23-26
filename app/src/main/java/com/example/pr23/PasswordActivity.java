package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordActivity extends AppCompatActivity {

    private StringBuilder entered = new StringBuilder();
    private View[] dotViews = new View[4];
    private TextView lastPressed = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        findViewById(R.id.btn_skip).setOnClickListener(v -> goNext());

        LinearLayout dotsContainer = findViewById(R.id.dots_password);
        float density = getResources().getDisplayMetrics().density;
        int sizePx = (int) (14 * density);
        int marginPx = (int) (8 * density);

        for (int i = 0; i < 4; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizePx, sizePx);
            lp.setMargins(marginPx, 0, marginPx, 0);
            dot.setLayoutParams(lp);
            dot.setBackground(getDrawable(R.drawable.bg_pin_dot_empty));
            dotsContainer.addView(dot);
            dotViews[i] = dot;
        }

        int[] keyIds = {R.id.pk_1, R.id.pk_2, R.id.pk_3, R.id.pk_4,
                R.id.pk_5, R.id.pk_6, R.id.pk_7, R.id.pk_8,
                R.id.pk_9, R.id.pk_0};
        String[] keyVals = {"1","2","3","4","5","6","7","8","9","0"};

        for (int i = 0; i < keyIds.length; i++) {
            final String val = keyVals[i];
            TextView key = findViewById(keyIds[i]);
            key.setOnClickListener(v -> {
                if (lastPressed != null) {
                    lastPressed.setBackground(getDrawable(R.drawable.bg_pin_key));
                    lastPressed.setTextColor(0xFF000000);
                }

                key.setBackground(getDrawable(R.drawable.bg_pin_key_active));
                key.setTextColor(0xFFFFFFFF);
                lastPressed = key;

                if (entered.length() < 4) {
                    entered.append(val);
                    updateDots();
                    if (entered.length() == 4) {
                        goNext();
                    }
                }
            });
        }

        // Удалить
        findViewById(R.id.pk_del).setOnClickListener(v -> {
            if (lastPressed != null) {
                lastPressed.setBackground(getDrawable(R.drawable.bg_pin_key));
                lastPressed.setTextColor(0xFF000000);
                lastPressed = null;
            }
            if (entered.length() > 0) {
                entered.deleteCharAt(entered.length() - 1);
                updateDots();
            }
        });
    }

    private void updateDots() {
        for (int i = 0; i < 4; i++) {
            dotViews[i].setBackground(getDrawable(
                    i < entered.length() ? R.drawable.bg_pin_dot : R.drawable.bg_pin_dot_empty
            ));
        }
    }

    private void goNext() {
        startActivity(new Intent(this, PatientCardActivity.class));
        finish();
    }
}