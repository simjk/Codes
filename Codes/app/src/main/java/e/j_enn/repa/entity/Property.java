package e.j_enn.repa.entity;

import android.os.Parcelable;

import java.util.ArrayList;

public class Property extends ArrayList<Parcelable> {

    private String housing_unit;
    private String town_area;
    private String address;
    private String property_type;
    private String price;
    private String user;
    private String tenure;
    private String floor_area;
    private String no_of_bed;
    private String no_of_bath;
    private String description;
    private byte[] property_Image;

    public Property() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFloor_area() {
        return floor_area;
    }

    public void setFloor_area(String floor_area) {
        this.floor_area = floor_area;
    }

    public String getNo_of_bed() {
        return no_of_bed;
    }

    public void setNo_of_bed(String no_of_bed) {
        this.no_of_bed = no_of_bed;
    }

    public String getNo_of_bath() {
        return no_of_bath;
    }

    public void setNo_of_bath(String no_of_bath) {
        this.no_of_bath = no_of_bath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public void setHousing_unit(String housing_unit) {
        this.housing_unit = housing_unit;
    }

    public String getHousing_unit() {
        return housing_unit;
    }

    public void setTown_area(String town_area) {
        this.town_area = town_area;
    }

    public String getTown_area() {
        return town_area;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public byte[] getProperty_Image() {
        return property_Image;
    }

    public void setProperty_Image(byte[] property_Image) {
        this.property_Image = property_Image;
    }

}
