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

//    @FormUrlEncoded
//    @POST("login/index.php")
//    Observable<StudentModuleCollectionDao> studentLoginCheck(@Field("tag") String tag, @Field("email") String email, @Field("password") String password);
    // in @Field "email" and "password" need to be matched with the php code of the api

    @FormUrlEncoded
    @POST("new_login/index.php")
    Observable<StudentModuleCollectionDao> studentLoginCheck(@Field("tag") String tag, @Field("email") String email, @Field("password") String password);


//    @FormUrlEncoded
//    @POST("studentModuleGET.php")
//    Observable<StudentModuleCollectionDao> loadStudentModule();

    @FormUrlEncoded
    @POST("studentModuleGet.php")
    Observable<StudentModuleCollectionDao> loadStudentModule(@Field("bla") String bla,@Query("student_id") int id);
//    Observable<StudentModuleCollectionDao> loadStudentModule(@Query("student_id") int id);


}
