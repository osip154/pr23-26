package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CodeActivity extends AppCompatActivity {

    private final String CORRECT_CODE = "1234";
    private StringBuilder entered = new StringBuilder();
    private TextView[] cells;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        cells = new TextView[]{
                findViewById(R.id.cell_1),
                findViewById(R.id.cell_2),
                findViewById(R.id.cell_3),
                findViewById(R.id.cell_4)
        };

        TextView tvTimer = findViewById(R.id.tv_timer);
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long ms) {
                tvTimer.setText("Отправить код повторно можно будет через " + (ms / 1000) + " секунд");
            }
            @Override
            public void onFinish() {
                tvTimer.setText("Отправить код повторно");
                tvTimer.setTextColor(0xFF2563EB);
                tvTimer.setOnClickListener(v -> {
                    entered.setLength(0);
                    updateCells();
                    start();
                    tvTimer.setTextColor(0xFF939393);
                    tvTimer.setOnClickListener(null);
                });
            }
        }.start();

        int[] keyIds = {R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_4,
                R.id.key_5, R.id.key_6, R.id.key_7, R.id.key_8,
                R.id.key_9, R.id.key_0};
        String[] keyVals = {"1","2","3","4","5","6","7","8","9","0"};

        for (int i = 0; i < keyIds.length; i++) {
            final String val = keyVals[i];
            findViewById(keyIds[i]).setOnClickListener(v -> {
                if (entered.length() < 4) {
                    entered.append(val);
                    updateCells();
                    if (entered.length() == 4) checkCode();
                }
            });
        }

        findViewById(R.id.key_del).setOnClickListener(v -> {
            if (entered.length() > 0) {
                entered.deleteCharAt(entered.length() - 1);
                updateCells();
            }
        });
    }

    private void updateCells() {
        for (int i = 0; i < 4; i++) {
            cells[i].setText(i < entered.length() ? String.valueOf(entered.charAt(i)) : "");
        }
    }

    private void checkCode() {
        if (entered.toString().equals(CORRECT_CODE)) {
            timer.cancel();
            startActivity(new Intent(this, PasswordActivity.class));
            finish();
        } else {
            entered.setLength(0);
            updateCells();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}