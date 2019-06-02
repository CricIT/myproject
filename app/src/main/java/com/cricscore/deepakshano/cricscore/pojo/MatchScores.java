package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deepak Shano on 1/13/2019.
 */

public class MatchScores {
        @SerializedName("cache")
        @Expose
        private Boolean cache;
        @SerializedName("score")
        @Expose
        private String score;

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
