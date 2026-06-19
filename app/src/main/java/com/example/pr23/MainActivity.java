package com.example.pr23;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static class Analysis {
        String name, days, price, category;
        Analysis(String name, String days, String price, String category) {
            this.name = name; this.days = days;
            this.price = price; this.category = category;
        }
    }

    private final List<Analysis> allAnalyses = new ArrayList<>();
    private LinearLayout analysisContainer;
    private String currentTab = "popular";
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analysisContainer = findViewById(R.id.analysis_container);
        EditText etSearch = findViewById(R.id.et_search);

        allAnalyses.add(new Analysis("ПЦР-тест на определение РНК коронавируса стандартный", "2 дня", "1800 ₽", "covid"));
        allAnalyses.add(new Analysis("Клинический анализ крови с лейкоцитарной формулировкой", "1 день", "690 ₽", "popular"));
        allAnalyses.add(new Analysis("Биохимический анализ крови, базовый", "1 день", "2440 ₽", "popular"));
        allAnalyses.add(new Analysis("СОЭ (венозная кровь)", "1 день", "390 ₽", "popular"));
        allAnalyses.add(new Analysis("Общий анализ мочи", "1 день", "350 ₽", "popular"));
        allAnalyses.add(new Analysis("Антитела IgG к COVID-19", "1 день", "950 ₽", "covid"));
        allAnalyses.add(new Analysis("Антитела IgM к COVID-19", "1 день", "950 ₽", "covid"));
        allAnalyses.add(new Analysis("Комплексный анализ щитовидной железы", "3 дня", "3200 ₽", "complex"));
        allAnalyses.add(new Analysis("Женский гормональный профиль", "3 дня", "4100 ₽", "complex"));
        allAnalyses.add(new Analysis("Комплексный анализ на аллергены", "5 дней", "5500 ₽", "complex"));

        buildNews();

        TextView tabPopular = findViewById(R.id.tab_popular);
        TextView tabCovid = findViewById(R.id.tab_covid);
        TextView tabComplex = findViewById(R.id.tab_complex);

        tabPopular.setOnClickListener(v -> setTab("popular", tabPopular, tabCovid, tabComplex));
        tabCovid.setOnClickListener(v -> setTab("covid", tabPopular, tabCovid, tabComplex));
        tabComplex.setOnClickListener(v -> setTab("complex", tabPopular, tabCovid, tabComplex));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                searchQuery = s.toString().trim().toLowerCase();
                updateList();
            }
        });

        findViewById(R.id.menu_results).setOnClickListener(v ->
                startActivity(new Intent(this, ResultsActivity.class)));
        findViewById(R.id.menu_support).setOnClickListener(v ->
                startActivity(new Intent(this, SupportActivity.class)));
        findViewById(R.id.menu_profile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        updateList();

        findViewById(R.id.btn_payment)
                .setOnClickListener(v -> {

                    startActivity(new Intent(this,
                            AddPaymentActivity.class));

                });
    }

    private void buildNews() {
        LinearLayout container = findViewById(R.id.news_container);
        String[][] news = {
                {"Чек-ап для\nмужчин", "9 исследований", "8000 ₽", "#7DB8F7"},
                {"Подготовка к\nвакцинации", "Комплекс анализов", "4000 ₽", "#F7A77D"},
                {"Женское\nздоровье", "12 исследований", "6500 ₽", "#7DF7A7"},
        };

        float density = getResources().getDisplayMetrics().density;
        int cardWidth = (int) (180 * density);
        int cardHeight = (int) (140 * density);
        int margin = (int) (12 * density);
        int padding = (int) (14 * density);

        for (String[] item : news) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.BOTTOM);
            card.setPadding(padding, padding, padding, padding);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(cardWidth, cardHeight);
            lp.setMargins(0, 0, margin, 0);
            card.setLayoutParams(lp);

            android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
            bg.setColor(android.graphics.Color.parseColor(item[3]));
            bg.setCornerRadius(16 * density);
            card.setBackground(bg);

            TextView title = new TextView(this);
            title.setText(item[0]);
            title.setTextSize(16);
            title.setTextColor(0xFFFFFFFF);
            title.setTypeface(null, android.graphics.Typeface.BOLD);

            TextView sub = new TextView(this);
            sub.setText(item[1]);
            sub.setTextSize(12);
            sub.setTextColor(0xFFFFFFFF);

            TextView price = new TextView(this);
            price.setText(item[2]);
            price.setTextSize(16);
            price.setTextColor(0xFFFFFFFF);
            price.setTypeface(null, android.graphics.Typeface.BOLD);

            card.addView(title);
            card.addView(sub);
            card.addView(price);
            container.addView(card);
        }
    }

    private void setTab(String tab, TextView t1, TextView t2, TextView t3) {
        currentTab = tab;
        t1.setBackgroundResource(tab.equals("popular") ? R.drawable.bg_tab_active : R.drawable.bg_tab_inactive);
        t2.setBackgroundResource(tab.equals("covid") ? R.drawable.bg_tab_active : R.drawable.bg_tab_inactive);
        t3.setBackgroundResource(tab.equals("complex") ? R.drawable.bg_tab_active : R.drawable.bg_tab_inactive);
        t1.setTextColor(tab.equals("popular") ? 0xFFFFFFFF : 0xFF000000);
        t2.setTextColor(tab.equals("covid") ? 0xFFFFFFFF : 0xFF000000);
        t3.setTextColor(tab.equals("complex") ? 0xFFFFFFFF : 0xFF000000);
        updateList();
    }

    private void updateList() {
        analysisContainer.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        int padding = (int) (16 * density);
        int margin = (int) (12 * density);
        int radius = (int) (12 * density);

        for (Analysis a : allAnalyses) {
            boolean matchTab = searchQuery.isEmpty() || a.category.equals(currentTab)
                    ? (searchQuery.isEmpty() ? a.category.equals(currentTab) : true)
                    : false;

            if (!searchQuery.isEmpty()) {
                if (!a.name.toLowerCase().contains(searchQuery)) continue;
            } else {
                if (!a.category.equals(currentTab)) continue;
            }

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(padding, padding, padding, padding);

            android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
            bg.setColor(0xFFF8F8F8);
            bg.setCornerRadius(radius);
            card.setBackground(bg);

            LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            cardLp.setMargins(0, 0, 0, margin);
            card.setLayoutParams(cardLp);

            TextView name = new TextView(this);
            name.setText(a.name);
            name.setTextSize(15);
            name.setTextColor(0xFF000000);
            name.setTypeface(null, android.graphics.Typeface.BOLD);
            card.addView(name);

            TextView days = new TextView(this);
            days.setText(a.days);
            days.setTextSize(13);
            days.setTextColor(0xFF939393);
            days.setPadding(0, (int)(6*density), 0, 0);
            card.addView(days);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowLp.topMargin = (int)(8*density);
            row.setLayoutParams(rowLp);

            TextView price = new TextView(this);
            price.setText(a.price);
            price.setTextSize(17);
            price.setTextColor(0xFF000000);
            price.setTypeface(null, android.graphics.Typeface.BOLD);
            LinearLayout.LayoutParams priceLp = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            price.setLayoutParams(priceLp);

            Button btn = new Button(this);
            btn.setText("Добавить");
            btn.setTextSize(14);
            btn.setTextColor(0xFFFFFFFF);

            android.graphics.drawable.GradientDrawable btnBg = new android.graphics.drawable.GradientDrawable();
            btnBg.setColor(0xFF2563EB);
            btnBg.setCornerRadius(radius);
            btn.setBackground(btnBg);

            row.addView(price);
            row.addView(btn);
            card.addView(row);
            analysisContainer.addView(card);
        }
    }
}