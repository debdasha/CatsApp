package ru.surdasha.cats.common;

public class Utils {

    public int countAspectRatioHeight(int screenWidth, int imageHeight, int imageWidth){
        int newImageHeight = imageHeight * screenWidth / imageWidth;
        return newImageHeight;
    }
}
