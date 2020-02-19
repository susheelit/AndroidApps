package com.e.salestracker.Modal;

import android.graphics.drawable.Drawable;

public class Social {

    public int image;
    public Drawable imageDrw;
    public String name;

    // flag when item swiped
    public boolean swiped = false;

    public Social() { }

    public Social(int image, String name ) {
        this.image = image;
        this.name = name;
    }

}
