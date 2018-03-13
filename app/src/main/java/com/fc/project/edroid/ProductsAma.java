package com.fc.project.edroid;

public class ProductsAma  {

    public String title;
    public String imgurl;
    public String desc;
    public String price;
    public String produrl;
    public String inStock;
    public String[] specs=new String[5] ;

    public ProductsAma() {

    }

    public ProductsAma(String title, String imgurl, String produrl,String price, String[] specs) {
        this.title = title;
        this.imgurl = imgurl;
        this.produrl = produrl;
        this.price=price;
        this.specs=specs;
       // this.flipkartSellingPrice=flipkartSellingPrice;
        //this.inStock=inStock;

    }

    public String[] getSpecs() {
        return specs;
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

   /* public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
*/
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
/*
    public String getFlipkartSellingPrice() {
        return flipkartSellingPrice;
    }

    public void setFlipkartSellingPrice(String flipkartSellingPrice) {
        this.flipkartSellingPrice = flipkartSellingPrice;
    }*/

//    public String getInStock() {
//        return inStock;
//    }
//
//    public void setInStock(String inStock) {
//        this.inStock = inStock;
//    }
}
