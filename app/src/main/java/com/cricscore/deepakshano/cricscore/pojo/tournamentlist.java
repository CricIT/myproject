
package com.cricscore.deepakshano.cricscore.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class tournamentlist {

    @SerializedName("overs")
    @Expose
    private Integer overs;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("entryFee")
    @Expose
    private Integer entryFee;
    @SerializedName("maxTeams")
    @Expose
    private Integer maxTeams;
    @SerializedName("minPlayers")
    @Expose
    private Integer minPlayers;
    @SerializedName("maxPlayers")
    @Expose
    private Integer maxPlayers;
    @SerializedName("winnerPrize")
    @Expose
    private String winnerPrize;
    @SerializedName("runnerPrize")
    @Expose
    private String runnerPrize;
    @SerializedName("matchInstructions")
    @Expose
    private String matchInstructions;
    @SerializedName("mom")
    @Expose
    private String mom;
    @SerializedName("mos")
    @Expose
    private String mos;
    @SerializedName("location")
    @Expose
    private String location;

  /*  @SerializedName("groundId")
    @Expose
    private List<String> groundId = null;*/


    @SerializedName("league")
    @Expose
    private Boolean league;
    @SerializedName("knockout")
    @Expose
    private Boolean knockout;
    @SerializedName("past")
    @Expose
    private Boolean past;
    @SerializedName("cancelled")
    @Expose
    private Boolean cancelled;
    @SerializedName("shareLink")
    @Expose
    private String shareLink;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("isLimited")
    @Expose
    private Boolean isLimited;
    @SerializedName("matchType")
    @Expose
    private Integer matchType;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("tournamentName")
    @Expose
    private String tournamentName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("officials")
    @Expose
    private java.util.List<Object> officials = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("distance")
    @Expose
    private Double distance;

   /* public List<String> getGroundId() {
        return groundId;
    }

    public void setGroundId(List<String> groundId) {
        this.groundId = groundId;
    }*/

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getOvers() {
        return overs;
    }

    public void setOvers(Integer overs) {
        this.overs = overs;
    }



    public Integer getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Integer entryFee) {
        this.entryFee = entryFee;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(Integer maxTeams) {
        this.maxTeams = maxTeams;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getWinnerPrize() {
        return winnerPrize;
    }

    public void setWinnerPrize(String winnerPrize) {
        this.winnerPrize = winnerPrize;
    }

    public String getRunnerPrize() {
        return runnerPrize;
    }

    public void setRunnerPrize(String runnerPrize) {
        this.runnerPrize = runnerPrize;
    }

    public String getMatchInstructions() {
        return matchInstructions;
    }

    public void setMatchInstructions(String matchInstructions) {
        this.matchInstructions = matchInstructions;
    }

    public String getMom() {
        return mom;
    }

    public void setMom(String mom) {
        this.mom = mom;
    }

    public String getMos() {
        return mos;
    }

    public void setMos(String mos) {
        this.mos = mos;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Boolean getLimited() {
        return isLimited;
    }

    public void setLimited(Boolean limited) {
        isLimited = limited;
    }

    public Boolean getLeague() {
        return league;
    }

    public void setLeague(Boolean league) {
        this.league = league;
    }

    public Boolean getKnockout() {
        return knockout;
    }

    public void setKnockout(Boolean knockout) {
        this.knockout = knockout;
    }

    public Boolean getPast() {
        return past;
    }

    public void setPast(Boolean past) {
        this.past = past;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Boolean getIsLimited() {
        return isLimited;
    }

    public void setIsLimited(Boolean isLimited) {
        this.isLimited = isLimited;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public java.util.List<Object> getOfficials() {
        return officials;
    }

    public void setOfficials(java.util.List<Object> officials) {
        this.officials = officials;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
