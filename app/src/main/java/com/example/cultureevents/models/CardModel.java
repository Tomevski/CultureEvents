package com.example.cultureevents.models;


/**
 * Model class used in [SliderAdapter].
 */
public class CardModel {

    private final int image;
    private final String title;
    private final String desc;

    public CardModel(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
