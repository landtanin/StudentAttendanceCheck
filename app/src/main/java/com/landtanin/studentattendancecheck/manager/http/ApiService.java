package com.landtanin.studentattendancecheck.manager.http;

import com.landtanin.studentattendancecheck.dao.StudentModuleCollectionDao;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public interface ApiService {

//    @POST("studentModuleGET.php")
//    Call<StudentModuleCollectionDao> loadStudentModule();

//    @FormUrlEncoded
//    @POST("studentModuleGET.php")
//    Observable<StudentModuleCollectionDao> loadStudentModule();

    // what does this style call again? Does it has something to do with RxJava or Realm?
    @FormUrlEncoded
    @POST("studentModuleGET.php")
    Observable<StudentModuleCollectionDao> loadStudentModule(@Field("bla") String bla, @Query("student_id") int id);
//    Observable<StudentModuleCollectionDao> loadStudentModule(@Query("student_id") int id);


}
