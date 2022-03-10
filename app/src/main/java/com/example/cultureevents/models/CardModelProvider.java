package com.example.cultureevents.models;

import com.example.cultureevents.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  Model used in [NewsFeedFragment]
 */
public class CardModelProvider {

    /**
     *
     * Data used in cards in [NewsFeedFragment]
     */
    public static List<CardModel> provideCardModels(){
        List <CardModel> cardModels = new ArrayList<>();
        cardModels.add(new CardModel(R.mipmap.image1_foreground," Охридско лето 2021","Локација:Антички театар Охрид\n 12.07.2021 Почеток 19:00 часот \n Свечено отварање на Охридско лето"));
        cardModels.add(new CardModel(R.mipmap.image2_foreground," Изложба","Локација: Музеј на македонска борба \n 25.12.2020 Почеток 19:00 часот \n Изложба 15 години на Босна и Херцеговина"));
        cardModels.add(new CardModel(R.mipmap.image3_foreground," 250 години Бетовен","Локација: Црква св.Софија Охрид \n 17.12.2020 Почеток 20:30 \n Симфониски концерт по повод 250 години од раѓањето"));
        cardModels.add(new CardModel(R.mipmap.image4_foreground," Бесачи","Локација: Македонски народен театар \n 30.12.2020 Почеток 20:00 \n Автор: Мартин Мекдона"));
        return cardModels;
    }
}
