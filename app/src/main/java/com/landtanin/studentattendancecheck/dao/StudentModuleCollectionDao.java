package com.landtanin.studentattendancecheck.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public class StudentModuleCollectionDao{

    @SerializedName("students")
    @Expose
    private List<StudentModuleDao> data = null;

    public List<StudentModuleDao> getData() {
        return data;
    }

    public void setData(List<StudentModuleDao> data) {
        this.data = data;
    }

    @SerializedName("students_login")
    @Expose
    private StudentLoginStatusDao studentsLogin;

    public StudentLoginStatusDao getStudentsLogin() {
        return studentsLogin;
    }

    public void setStudentsLogin(StudentLoginStatusDao studentsLogin) {
        this.studentsLogin = studentsLogin;
    }
}
