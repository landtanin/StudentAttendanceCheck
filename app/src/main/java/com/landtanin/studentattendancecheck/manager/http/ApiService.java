package com.landtanin.studentattendancecheck.manager.http;

import com.landtanin.studentattendancecheck.dao.StudentModuleCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public interface ApiService {

    @POST("studentModuleGET.php")
    Call<StudentModuleCollectionDao> loadStudentModule();


}
