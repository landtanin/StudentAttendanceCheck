package com.landtanin.studentattendancecheck.manager.http;

import com.landtanin.studentattendancecheck.dao.StudentModuleCollectionDao;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by landtanin on 4/10/2017 AD.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("studentLogin/index.php")
    Observable<StudentModuleCollectionDao> studentLoginCheck
            (@Field("tag") String tag,
             @Field("email") String email,
             @Field("password") String password);

    @FormUrlEncoded
    @POST("studentModuleUpdate.php")
    Call<ResponseBody> attendanceUpdate
            (@Field("status") String status,
             @Field("student_id") int student_id,
             @Field("module_id") String module_id);

    @GET("studentModuleGet.php")
    Observable<StudentModuleCollectionDao> loadStudentModule
            (@Query("student_id") int id);

}
