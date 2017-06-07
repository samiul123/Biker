package com.example.lotuscomputer.biker;

/**
 * Created by Lotus Computer on 03-Jun-17.
 */

public class RowItem {
    private String bike_name;
    private String bike_price;
    private byte[] bike_image;



    public RowItem(String bike_name, String bike_price, byte[] bike_image){
        this.bike_name = bike_name;
        this.bike_price = bike_price;
        this.bike_image = bike_image;

    }

    public String getBike_name() {
        return bike_name;
    }

    public void setBike_name(String bike_name) {
        this.bike_name = bike_name;
    }

    public String getBike_price() {
        return bike_price;
    }

    public void setBike_price(String bike_price) {
        this.bike_price = bike_price;
    }

    public byte[] getBike_image() {
        return bike_image;
    }

    public void setBike_image(byte[] bike_image) {
        this.bike_image = bike_image;
    }


}
