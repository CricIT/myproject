package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginatedGroundListPoJo extends GeneralPojoClass {


    @SerializedName("nextPage")
    @Expose
    private Integer nextPage;

    @SerializedName("list")
    @Expose

    private List<HostingGroundList> HostingGroundList = null;

    public List<HostingGroundList> getHostingGroundList() {
        return HostingGroundList;
    }

    public void setHostingGroundList(List<com.cricscore.deepakshano.cricscore.pojo.HostingGroundList> hostingGroundList) {
        HostingGroundList = hostingGroundList;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }


}
