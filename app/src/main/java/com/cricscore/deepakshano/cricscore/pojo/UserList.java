package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList extends GeneralPojoClass {

    @SerializedName("data")
    @Expose
    private List<MemberInfo> userDetails;

    public List<MemberInfo> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<MemberInfo> userDetails) {
        this.userDetails = userDetails;
    }
}
