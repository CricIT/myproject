
package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TournamentListPojoClass extends GeneralPojoClass {

    @SerializedName("list")
    @Expose
    private List<tournamentlist> list = null;
    @SerializedName("groundList")
    @Expose
    private List<GroundList> groundList = null;

    @SerializedName("nextPage")
    @Expose
    private Integer nextPage;

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public java.util.List<tournamentlist> getList() {
        return list;
    }

    public void setList(java.util.List<tournamentlist> list) {
        this.list = list;
    }

    public java.util.List<GroundList> getGroundList() {
        return groundList;
    }

    public void setGroundList(java.util.List<GroundList> groundList) {
        this.groundList = groundList;
    }

}
