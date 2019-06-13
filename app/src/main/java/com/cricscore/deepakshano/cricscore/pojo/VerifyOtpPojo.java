package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class VerifyOtpPojo extends GeneralPojoClass {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
