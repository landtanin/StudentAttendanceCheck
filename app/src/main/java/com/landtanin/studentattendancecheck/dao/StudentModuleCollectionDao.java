package com.landtanin.studentattendancecheck.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public class StudentModuleCollectionDao {

    @SerializedName("students")
    @Expose
    private List<StudentModuleDao> data = null;

    public StudentModuleCollectionDao() {

    }

    public List<StudentModuleDao> getData() {
        return data;
    }

    public void setData(List<StudentModuleDao> data) {
        this.data = data;
    }
}
