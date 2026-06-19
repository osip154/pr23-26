package com.example.pr23;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PatientCardActivity extends AppCompatActivity {

    private boolean genderSelected = false;
    private boolean dateSelected = false;
    private EditText etName, etPatronymic, etSurname;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_card);

        etName = findViewById(R.id.et_name);
        etPatronymic = findViewById(R.id.et_patronymic);
        etSurname = findViewById(R.id.et_surname);
        TextView etBirthdate = findViewById(R.id.et_birthdate);
        Spinner spinnerGender = findViewById(R.id.spinner_gender);
        btnCreate = findViewById(R.id.btn_create);
        TextView btnSkip = findViewById(R.id.btn_skip);

        btnCreate.setEnabled(false);

        etBirthdate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        etBirthdate.setText(d + "." + (m + 1) + "." + y);
                        dateSelected = true;
                        checkFields();
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Пол", "Мужской", "Женский"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                genderSelected = pos > 0;
                checkFields();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) { checkFields(); }
        };

        etName.addTextChangedListener(watcher);
        etPatronymic.addTextChangedListener(watcher);
        etSurname.addTextChangedListener(watcher);

        btnCreate.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        btnSkip.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void checkFields() {
        boolean filled = !etName.getText().toString().trim().isEmpty()
                && !etPatronymic.getText().toString().trim().isEmpty()
                && !etSurname.getText().toString().trim().isEmpty()
                && dateSelected
                && genderSelected;
        btnCreate.setEnabled(filled);
    }
}