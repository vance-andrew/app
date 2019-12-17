package com.app.virtualcampus.Model;

public class Faculty {

    String id, name, designation, dept, classTaught, room, floor, building, pic;

    public Faculty(String id, String name, String designation, String dept, String classTaught, String room, String floor, String building, String pic) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.dept = dept;
        this.classTaught = classTaught;
        this.room = room;
        this.floor = floor;
        this.building = building;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getClassTaught() {
        return classTaught;
    }

    public void setClassTaught(String classTaught) {
        this.classTaught = classTaught;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
