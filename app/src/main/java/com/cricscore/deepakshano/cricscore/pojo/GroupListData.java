
package com.cricscore.deepakshano.cricscore.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupListData {

    @SerializedName("groupPicUrl")
    @Expose
    private String groupPicUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("teams")
    @Expose
    private List<Object> teams = null;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("shareLink")
    @Expose
    private String shareLink;
    @SerializedName("deferLink")
    @Expose
    private String deferLink;
    @SerializedName("areaName")
    @Expose
    private String areaName;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("members")
    @Expose
    private List<GroupMembersList> members = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getGroupPicUrl() {
        return groupPicUrl;
    }

    public void setGroupPicUrl(String groupPicUrl) {
        this.groupPicUrl = groupPicUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Object> getTeams() {
        return teams;
    }

    public void setTeams(List<Object> teams) {
        this.teams = teams;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getDeferLink() {
        return deferLink;
    }

    public void setDeferLink(String deferLink) {
        this.deferLink = deferLink;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GroupMembersList> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMembersList> members) {
        this.members = members;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
