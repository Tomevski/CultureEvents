package com.example.cultureevents.ui.news.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cultureevents.R;
import com.example.cultureevents.models.CardModel;

import java.util.List;

/**
 * Slider adapter used in [NewsFeedFragment].
 */
public class SliderAdapter extends PagerAdapter {

    private List<CardModel> cardModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public SliderAdapter(List<CardModel> cardModels, Context context) {
        this.cardModels = cardModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);

        imageView.setImageResource(cardModels.get(position).getImage());
        title.setText(cardModels.get(position).getTitle());
        desc.setText(cardModels.get(position).getDesc());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
