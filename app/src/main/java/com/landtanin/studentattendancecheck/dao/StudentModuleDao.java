package com.landtanin.studentattendancecheck.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public class StudentModuleDao extends RealmObject implements RealmModel{
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("moduleId")
    @Expose
    private String moduleId;
    @SerializedName("startDate")
    @Expose
    private Date startDate;
    @SerializedName("endDate")
    @Expose
    private Date endDate;
    @SerializedName("repeatStatus")
    @Expose
    private String repeatStatus;
    @SerializedName("checkInStart")
    @Expose
    private Date checkInStart;
    @SerializedName("checkInEnd")
    @Expose
    private Date checkInEnd;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("LocLat")
    @Expose
    private double locLat;
    @SerializedName("LocLng")
    @Expose
    private double locLng;
    @SerializedName("ModATTRate")
    @Expose
    private String modATTRate;
    @SerializedName("ModStatus")
    @Expose
    private String modStatus;
    @SerializedName("Day")
    @Expose
    private String day;
    @SerializedName("Description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRepeatStatus() {
        return repeatStatus;
    }

    public void setRepeatStatus(String repeatStatus) {
        this.repeatStatus = repeatStatus;
    }

    public Date getCheckInStart() {
        return checkInStart;
    }

    public void setCheckInStart(Date checkInStart) {
        this.checkInStart = checkInStart;
    }

    public Date getCheckInEnd() {
        return checkInEnd;
    }

    public void setCheckInEnd(Date checkInEnd) {
        this.checkInEnd = checkInEnd;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public double getLocLat() {
        return locLat;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLng() {
        return locLng;
    }

    public void setLocLng(double locLng) {
        this.locLng = locLng;
    }

    public String getModATTRate() {
        return modATTRate;
    }

    public void setModATTRate(String modATTRate) {
        this.modATTRate = modATTRate;
    }

    public String getModStatus() {
        return modStatus;
    }

    public void setModStatus(String modStatus) {
        this.modStatus = modStatus;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
