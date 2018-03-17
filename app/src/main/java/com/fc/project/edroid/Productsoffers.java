package com.fc.project.edroid;


import java.util.Calendar;
import java.util.Locale;

public class Productsoffers {

    public String title;
    public String imgUrl;
    public String desc;
    public String produrl;
    public String[] Endtime=new String[2];
    public long endmil;
    public Productsoffers() {

    }

    public Productsoffers(String title, String imgUrl, String desc,String produrl,long endmil) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.produrl=produrl;
        this.endmil=endmil;
    }


    public String[] getEndtime() {
        datetime(endmil, Endtime);
        return Endtime; }


    private void datetime(long mil, String[] stringarr) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(mil);  //here your time in miliseconds
        stringarr[0] = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) + ":" + cl.get(Calendar.YEAR);
        stringarr[1] = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
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



    public String getProdUrl() {
        return produrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


//    public String getInStock() {
//        return inStock;
//    }
//
//    public void setInStock(String inStock) {
//        this.inStock = inStock;
//    }
}
