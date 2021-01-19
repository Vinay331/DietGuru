package com.srikanth.royal.dietg.Model;

public class Items {


    private String  title,shortdescription,description,image,date,time,iid;

    public Items() {
    }

    public Items(String title, String shortdescription, String description, String image, String date, String time, String iid) {
        this.title = title;
        this.shortdescription = shortdescription;
        this.description = description;
        this.image = image;
        this.date = date;
        this.time = time;
        this.iid = iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
