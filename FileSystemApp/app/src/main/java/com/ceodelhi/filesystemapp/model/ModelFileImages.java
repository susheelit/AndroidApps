package com.ceodelhi.filesystemapp.model;

import java.io.Serializable;

public class ModelFileImages implements Serializable {

    private String ImagePath;

    public ModelFileImages(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
