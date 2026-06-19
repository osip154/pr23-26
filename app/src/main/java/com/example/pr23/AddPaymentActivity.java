package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPaymentActivity extends AppCompatActivity {

    private RadioGroup paymentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        paymentGroup = findViewById(R.id.payment_group);
        Button btnContinue = findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(v -> {

            int checkedId = paymentGroup.getCheckedRadioButtonId();

            if (checkedId == -1) {
                Toast.makeText(this,
                        "Выберите способ оплаты",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(new Intent(this,
                    TrackingActivity.class));
        });
    }
}