package com.fc.project.edroid;

public class ProductsEba  {

    public String title;
    public String imgurl;
    public String price;
    public String produrl;
    public ProductsEba() {

    }

    public ProductsEba(String title, String imgurl, String produrl,String price) {
        this.title = title;
        this.imgurl = imgurl;
        this.produrl = produrl;
        this.price=price;
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

    public String getProdUrl() {
        return produrl;
    }

    public void setProdUrl(String produrl) {
        this.produrl = produrl;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}