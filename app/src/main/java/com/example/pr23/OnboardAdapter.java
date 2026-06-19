package com.example.pr23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class OnboardAdapter extends RecyclerView.Adapter<OnboardAdapter.OnboardVH> {

    private final ViewPager2 viewPager;

    private final String[] titles = {"Анализы", "Уведомления", "Мониторинг"};
    private final String[] descs = {
            "Экспресс сбор и получение проб",
            "Вы быстро узнаете о результатах",
            "Наши врачи всегда наблюдают\nза вашими показателями здоровья"
    };
    private final int[] imgs = {R.drawable.colbs, R.drawable.doctor_with_gerl, R.drawable.doctor_gerl};

    public OnboardAdapter(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public OnboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboard, parent, false);
        return new OnboardVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardVH holder, int position) {
        holder.t.setText(titles[position]);
        holder.d.setText(descs[position]);
        holder.i.setImageResource(imgs[position]);
        buildDots(holder.dotsContainer, position, getItemCount());
    }

    private void buildDots(LinearLayout container, int currentPos, int count) {
        container.removeAllViews();
        Context ctx = container.getContext();
        float density = ctx.getResources().getDisplayMetrics().density;
        int sizePx = (int) (10 * density);
        int marginPx = (int) (5 * density);

        for (int i = 0; i < count; i++) {
            View dot = new View(ctx);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizePx, sizePx);
            lp.setMargins(marginPx, 0, marginPx, 0);
            dot.setLayoutParams(lp);
            dot.setBackground(ctx.getDrawable(
                    i == currentPos ? R.drawable.dot_active : R.drawable.dot_inactive
            ));

            final int targetPage = i;
            dot.setOnClickListener(v -> viewPager.setCurrentItem(targetPage, true));

            container.addView(dot);
        }
    }

    @Override
    public int getItemCount() { return 3; }

    static class OnboardVH extends RecyclerView.ViewHolder {
        TextView t, d;
        ImageView i;
        LinearLayout dotsContainer;

        public OnboardVH(View v) {
            super(v);
            t = v.findViewById(R.id.item_title);
            d = v.findViewById(R.id.item_desc);
            i = v.findViewById(R.id.item_img);
            dotsContainer = v.findViewById(R.id.dots_container);
        }
    }
}