package com.cricscore.deepakshano.cricscore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class HosttournamentParametersModelClass {
    @SerializedName("tournamentName")
    @Expose
    private String tournamentName;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("time")
    @Expose
    private Integer time;

    @SerializedName("matchType")
    @Expose
    private Integer matchType;

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

    @SerializedName("matchInstructions")
    @Expose
    private String matchInstructions;

    @SerializedName("mom")
    @Expose
    private String mom;

    @SerializedName("mos")
    @Expose
    private String mos;

    @SerializedName("winnerPrize")
    @Expose
    private String winnerPrize;

    @SerializedName("runnerPrize")
    @Expose
    private String runnerPrize;

    @SerializedName("isLimited")
    @Expose
    private Boolean isLimited;

    @SerializedName("groundId")
    @Expose
    private ArrayList<String> groundId;

    @SerializedName("overs")
    @Expose
    private Integer overs;

    @SerializedName("hostId")
    @Expose
    private String hostId;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
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

    public Boolean getLimited() {
        return isLimited;
    }

    public void setLimited(Boolean limited) {
        isLimited = limited;
    }

    public Integer getOvers() {
        return overs;
    }

    public void setOvers(Integer overs) {
        this.overs = overs;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public ArrayList<String> getGroundId() {
        return groundId;
    }

    public void setGroundId(ArrayList<String> groundId) {
        this.groundId = groundId;
    }

}

