package com.example.cultureevents;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultureevents.models.CardModel;

import java.util.ArrayList;
import java.util.List;


public class NewsFeedFragment extends Fragment {

    View view;
    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    List<CardModel> cardModels;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        // Inflate the layout for this fragment
        cardModels = new ArrayList<>();
        cardModels.add(new CardModel(R.mipmap.image1_foreground," Охридско лето 2021","Локација:Антички театар Охрид\n 12.07.2021 Почеток 19:00 часот \n Свечено отварање на Охридско лето"));
        cardModels.add(new CardModel(R.mipmap.image2_foreground," Изложба","Локација: Музеј на македонска борба \n 25.12.2020 Почеток 19:00 часот \n Изложба 15 години на Босна и Херцеговина"));
        cardModels.add(new CardModel(R.mipmap.image3_foreground," 250 години Бетовен","Локација: Црква св.Софија Охрид \n 17.12.2020 Почеток 20:30 \n Симфониски концерт по повод 250 години од раѓањето"));
        cardModels.add(new CardModel(R.mipmap.image4_foreground," Бесачи","Локација: Македонски народен театар \n 30.12.2020 Почеток 20:00 \n Автор: Мартин Мекдона"));

        sliderAdapter = new SliderAdapter(cardModels,getActivity());

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {getResources().getColor(R.color.teal_200)};

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (sliderAdapter.getCount() - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position ],
                                    colors[position + 1]
                            )
                    );
                }else{
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });






        return view;
    }


}