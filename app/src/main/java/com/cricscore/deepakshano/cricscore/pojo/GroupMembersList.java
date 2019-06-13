
package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupMembersList {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
