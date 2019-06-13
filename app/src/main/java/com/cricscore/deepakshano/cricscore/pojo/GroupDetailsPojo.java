
package com.cricscore.deepakshano.cricscore.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupDetailsPojo extends GeneralPojoClass {

    @SerializedName("data")
    @Expose
    private GroupDetails groupDetails;
    @SerializedName("memberInfo")
    @Expose
    private List<MemberInfo> memberInfo = null;

    public GroupDetails getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(GroupDetails groupDetails) {
        this.groupDetails = groupDetails;
    }

    public List<MemberInfo> getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(List<MemberInfo> memberInfo) {
        this.memberInfo = memberInfo;
    }
}
