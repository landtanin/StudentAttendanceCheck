package com.landtanin.studentattendancecheck.adapter;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class AddModuleItem {

    public String moduleText;
    public String moduleId;
    public boolean checkboxStatus;

    public AddModuleItem(String moduleText, String moduleId, boolean checkboxStatus) {
        this.moduleText = moduleText;
        this.moduleId = moduleId;
        this.checkboxStatus = checkboxStatus;
    }



    public String getModuleText() {
        return moduleText;
    }

    public void setModuleText(String moduleText) {
        this.moduleText = moduleText;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isCheckboxStatus() {
        return checkboxStatus;
    }

    public void setCheckboxStatus(boolean checkboxStatus) {
        this.checkboxStatus = checkboxStatus;
    }
}
