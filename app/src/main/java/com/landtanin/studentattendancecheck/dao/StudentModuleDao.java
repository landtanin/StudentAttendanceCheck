package com.landtanin.studentattendancecheck.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public class StudentModuleDao {

    @SerializedName("_id")
    private int _id;
    @SerializedName("name")
    private String name;
    @SerializedName("moduleId")
    private String moduleId;
    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;
    @SerializedName("repeatStatus")
    private String repeatStatus;
    @SerializedName("checkInStart")
    private Date checkInStart;
    @SerializedName("checkInEnd")
    private Date checkInEnd;
    @SerializedName("LocLat")
    private double locLat;
    @SerializedName("LocLng")
    private double locLng;
    @SerializedName("ModATTRate")
    private int modATTRate;
    @SerializedName("ModStatus")
    private String modStatus;
    @SerializedName("Day")
    private String day;
    @SerializedName("Description")
    private String description;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int getModATTRate() {
        return modATTRate;
    }

    public void setModATTRate(int modATTRate) {
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
