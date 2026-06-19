package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        EditText etEmail = findViewById(R.id.et_email);
        Button btnNext = findViewById(R.id.btn_next);
        Button btnYandex = findViewById(R.id.btn_yandex);

        btnNext.setEnabled(false);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                btnNext.setEnabled(s.toString().trim().length() > 0);
            }
        });

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, CodeActivity.class));
        });

        btnYandex.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, CodeActivity.class));
        });
    }
}