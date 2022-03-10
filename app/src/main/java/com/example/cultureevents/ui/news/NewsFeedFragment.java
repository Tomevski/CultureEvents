package com.example.cultureevents.ui.news;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.annotation.DimenRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultureevents.R;
import com.example.cultureevents.ui.news.adapter.SliderAdapter;
import com.example.cultureevents.models.CardModel;
import com.example.cultureevents.models.CardModelProvider;

import java.util.List;

/**
 * NewsFeedFragment used in MainActivity
 */
public class NewsFeedFragment extends Fragment {

    private View view;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private List<CardModel> cardModels;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        // Inflate the layout for this fragment
        cardModels = CardModelProvider.provideCardModels();
        sliderAdapter = new SliderAdapter(cardModels, getActivity());

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {getResources().getColor(R.color.teal_200)};

        colors = colors_temp;
        initListeners();
        return view;
    }

    private void initListeners() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (sliderAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //No-op
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //No-op
            }
        });
    }
}