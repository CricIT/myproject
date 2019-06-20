package com.cricscore.deepakshano.cricscore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class CreateGroupModelClass {
    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("lng")
    @Expose
    private Double lng;

    @SerializedName("areaName")
    @Expose
    private String areaName;


    @SerializedName("groupName")
    @Expose
    private String groupName;

    @SerializedName("description")
    @Expose
    private String description;

    public Integer getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(Integer grouptype) {
        this.grouptype = grouptype;
    }

    @SerializedName("grouptype")
    @Expose
    private Integer grouptype;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
