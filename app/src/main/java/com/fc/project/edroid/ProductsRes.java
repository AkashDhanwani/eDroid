package com.fc.project.edroid;

public class ProductsRes {

    public String title;
    public String imgurl;
    public String prods[][];
    public ProductsRes() {

    }

    public ProductsRes(String title, String imgurl, String[][] prods) {
        this.title = title;
        this.imgurl = imgurl;
        this.prods = prods;
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

    public String[][] getProds() {
        return prods;
    }

    public void setProds(String produrl) {
        this.prods = prods;
    }
    }