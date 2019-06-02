package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MatchList {

    @SerializedName("matches")
    @Expose
    private List<matchlist> matchlist = new ArrayList<matchlist>();

    public List<MatchList.matchlist> getMatchlist() {
        return matchlist;
    }

    public void setMatchlist(List<MatchList.matchlist> matchlist) {
        this.matchlist = matchlist;
    }

    public static class matchlist {
        @SerializedName("unique_id")
        private String id;
        @SerializedName("team-2")
        private String title;
        @SerializedName("team-1")
        private String brief;
        @SerializedName("toss_winner_team")
        private String fileSource;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getFileSource() {
            return fileSource;
        }

        public void setFileSource(String fileSource) {
            this.fileSource = fileSource;
        }
    }

}