
package com.cricscore.deepakshano.cricscore.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllGroupsListPojoClass extends  GeneralPojoClass {

    @SerializedName("data")
    @Expose
    private List<GroupListData> data = null;

    public List<GroupListData> getData() {
        return data;
    }

    public void setData(List<GroupListData> data) {
        this.data = data;
    }

}
