package com.landtanin.studentattendancecheck.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public class StudentModuleCollectionDao{

    @SerializedName("modules")
    @Expose
    private List<StudentModuleDao> data = null;

    public List<StudentModuleDao> getData() {
        return data;
    }

    public void setData(List<StudentModuleDao> data) {
        this.data = data;
    }

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private User user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
