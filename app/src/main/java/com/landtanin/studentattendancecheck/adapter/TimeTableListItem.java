package com.landtanin.studentattendancecheck.adapter;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class TimeTableListItem {

    public String moduleNameTxt, moduleIdTxt, statusTxt, timeTxt, locationTxt;


    public TimeTableListItem(String moduleNameTxt, String moduleIdTxt, String statusTxt, String timeTxt, String locationTxt) {
        this.moduleNameTxt = moduleNameTxt;
        this.moduleIdTxt = moduleIdTxt;
        this.statusTxt = statusTxt;
        this.timeTxt = timeTxt;
        this.locationTxt = locationTxt;
    }

    public String getModuleNameTxt() {
        return moduleNameTxt;
    }

    public void setModuleNameTxt(String moduleNameTxt) {
        this.moduleNameTxt = moduleNameTxt;
    }

    public String getModuleIdTxt() {
        return moduleIdTxt;
    }

    public void setModuleIdTxt(String moduleIdTxt) {
        this.moduleIdTxt = moduleIdTxt;
    }

    public String getStatusTxt() {
        return statusTxt;
    }

    public void setStatusTxt(String statusTxt) {
        this.statusTxt = statusTxt;
    }

    public String getTimeTxt() {
        return timeTxt;
    }

    public void setTimeTxt(String timeTxt) {
        this.timeTxt = timeTxt;
    }

    public String getLocationTxt() {
        return locationTxt;
    }

    public void setLocationTxt(String locationTxt) {
        this.locationTxt = locationTxt;
    }
}
