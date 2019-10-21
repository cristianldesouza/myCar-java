package com.example.mycar.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Fuel extends RealmObject{
    @PrimaryKey
    private String id;
    private double currentKm;
    private double litersFuelled;
    private String brand;

    private Date pureDate;

    @Ignore
    private Calendar date;

    public Fuel(){
        id = UUID.randomUUID().toString();
    }

    public double getCurrentKm() {
        return currentKm;
    }

    public void setCurrentKm(double currentKm) {
        this.currentKm = currentKm;
    }

    public double getLitersFuelled() {
        return litersFuelled;
    }

    public void setLitersFuelled(double litersFuelled) {
        this.litersFuelled = litersFuelled;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Calendar getDate() {
        if (pureDate != null){
            date = Calendar.getInstance();
            date.setTime(pureDate);
        }
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
        this.pureDate = date.getTime();
    }
}

