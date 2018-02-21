package com.fc.project.edroid;

/**
 * Created by LakhwaniPc on 18-02-2018.
 */

public class Products  {

    public String title;
    public String imgUrl;
    public String desc;

    public Products() {

    }

    public Products(String title, String imgUrl, String desc) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
