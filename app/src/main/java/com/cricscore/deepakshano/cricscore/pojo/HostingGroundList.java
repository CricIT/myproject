
package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HostingGroundList {

    @SerializedName("groundName")
    @Expose
    private String groundName;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("amenities")
    @Expose
    private java.util.List<Object> amenities = null;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cityCode")
    @Expose
    private Object cityCode;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("ratingCount")
    @Expose
    private Integer ratingCount;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("createdTS")
    @Expose
    private String createdTS;
    @SerializedName("modTS")
    @Expose
    private String modTS;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("distance")
    @Expose
    private Double distance;

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public java.util.List<Object> getAmenities() {
        return amenities;
    }

    public void setAmenities(java.util.List<Object> amenities) {
        this.amenities = amenities;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getCityCode() {
        return cityCode;
    }

    public void setCityCode(Object cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(String createdTS) {
        this.createdTS = createdTS;
    }

    public String getModTS() {
        return modTS;
    }

    public void setModTS(String modTS) {
        this.modTS = modTS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
