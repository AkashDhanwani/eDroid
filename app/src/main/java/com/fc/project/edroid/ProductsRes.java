package com.fc.project.edroid;

public class ProductsRes {

    public String title;
    public String imgurl;
    public String pid;
    public ProductsRes() {

    }

    public ProductsRes(String title, String imgurl, String pid) {
        this.title = title;
        this.imgurl = imgurl;
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgurl;
    }

    public void setImgUrl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPid() {
        return pid;
    }

    }