package com.cricscore.deepakshano.cricscore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToggleAdmin {

    @SerializedName("groupId")
    @Expose
    String groupid;

    @SerializedName("bool")
    @Expose
    int bool;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public int getBool() {
        return bool;
    }

    public void setBool(int bool) {
        this.bool = bool;
    }
}
