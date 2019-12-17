package com.app.virtualcampus.Model;

public class Building {

    String ID , name , lat , lng , pic;

    public Building(String ID, String name, String lat, String lng, String pic) {
        this.ID = ID;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.pic = pic;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
