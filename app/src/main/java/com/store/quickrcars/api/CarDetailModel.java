package com.store.quickrcars.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 11-07-2015.
 */
public class CarDetailModel {

    @Expose
    private String name;
    @Expose
    private String image;
    @Expose
    private String price;
    @Expose
    private String brand;
    @Expose
    private String type;
    @Expose
    private String rating;
    @Expose
    private String color;
    @SerializedName("engine_cc")
    @Expose
    private String engineCc;
    @Expose
    private String mileage;
    @SerializedName("abs_exist")
    @Expose
    private String absExist;
    @Expose
    private String description;
    @Expose
    private String link;
    @Expose
    private List<City> cities = new ArrayList<City>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     *
     * @param brand
     * The brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     * The engineCc
     */
    public String getEngineCc() {
        return engineCc;
    }

    /**
     *
     * @param engineCc
     * The engine_cc
     */
    public void setEngineCc(String engineCc) {
        this.engineCc = engineCc;
    }

    /**
     *
     * @return
     * The mileage
     */
    public String getMileage() {
        return mileage;
    }

    /**
     *
     * @param mileage
     * The mileage
     */
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    /**
     *
     * @return
     * The absExist
     */
    public String getAbsExist() {
        return absExist;
    }

    /**
     *
     * @param absExist
     * The abs_exist
     */
    public void setAbsExist(String absExist) {
        this.absExist = absExist;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     * The cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     *
     * @param cities
     * The cities
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public class City
    {
        @Expose
        private String city;
        @Expose
        private String users;

        /**
         *
         * @return
         * The city
         */
        public String getCity() {
            return city;
        }

        /**
         *
         * @param city
         * The city
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         *
         * @return
         * The users
         */
        public String getUsers() {
            return users;
        }

        /**
         *
         * @param users
         * The users
         */
        public void setUsers(String users) {
            this.users = users;
        }

    }

    @Override
    public String toString() {
        return name;
    }
}
