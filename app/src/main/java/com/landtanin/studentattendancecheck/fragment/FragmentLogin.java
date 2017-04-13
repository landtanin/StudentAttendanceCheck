package com.landtanin.studentattendancecheck.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.dao.StudentLoginStatusDao;
import com.landtanin.studentattendancecheck.dao.StudentModuleCollectionDao;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.FragmentLoginBinding;
import com.landtanin.studentattendancecheck.manager.HttpManager;
import com.landtanin.studentattendancecheck.manager.http.ApiService;
import com.landtanin.studentattendancecheck.util.Utils;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentLogin extends Fragment {

    FragmentLoginBinding b;
    String email, password;
    private static final String tag = "login";
    private ProgressDialog loginProgressDialog;

    public FragmentLogin() {
        super();
    }

    public static FragmentLogin newInstance() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View rootView = b.getRoot();
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here


    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = b.edtLoginEmail.getText().toString();
                password = b.edtLoginPassword.getText().toString();

//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... params) {
//
//                        getLogin();
//
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//
//                        RealmResults<StudentLoginStatusDao> studentLoginStatusDao = Realm.getDefaultInstance()
//                                .where(StudentLoginStatusDao.class)
//                                .findAllAsync();
//
//                        if (studentLoginStatusDao.get(0).getSuccess() == 1) {
//
//                            getStudent(studentLoginStatusDao.get(0).getUser().getStudentId());
//เ
//                            Log.w("BACKGROUND", "onPostExecute()");
//
//                        }
//
//                    }
//                }.execute();

                Log.w("BACKGROUND", "OFF from onPostExecute()");

                getLogin(); // MOVE TO BACKGROUND

                //TODO: make sure getLogin is fnished before the id is send to getStudent in link 105

//                // if success = 1, intent, else, toast fail
//                RealmResults<StudentLoginStatusDao> studentLoginStatusDao = Realm.getDefaultInstance().where(StudentLoginStatusDao.class)
//                        .findAllAsync();
//                        // alternative from findAll
//
//                while(studentLoginStatusDao==null){
//
//                    Log.w("LOGIN", studentLoginStatusDao.toString());
//
//                }
//
//                if (studentLoginStatusDao.get(0).getSuccess()==1) {
//
//                     //TODO: sharepreference to store login state
//
//                    //retrieve student data and check whether he or she already registered for any module
//                    getStudent(studentLoginStatusDao.get(0).getUser().getStudentId()); // dump data into Realm
//
//                    RealmResults<StudentModuleDao> studentModuleDao = Realm.getDefaultInstance().where(StudentModuleDao.class)
//                          .findAllAsync();
//
//                    Log.w("Mr." + studentLoginStatusDao.get(0).getUser().getName() + " first module",
//                            studentModuleDao.get(0).getName().toString());
//
////                    Intent intent = new Intent(getActivity(), MainActivity.class);
////
////                    if (studentModuleDao.get(0) != null) {
////
////                        // he is ...... who study ......
////                        Log.w("he is " + studentLoginStatusDao.get(0).getUser().getName() + " who study ",
////                                studentModuleDao.get(0).getName());
////
////                        intent.putExtra("add_or_not", "not_add");
////
////                    } else {
////
////                        intent.putExtra("add_or_not", "add");
////
////                    }
////
////                    startActivity(intent);
//
//
//                } else {
//                    Utils.getInstance().onHoneyToast("invalid email or password");
//                }


            }
        });

    }

    private void getLogin() {


        ApiService apiService = HttpManager.getInstance().create(ApiService.class);
//        apiService.loadStudentModule(Authorization,Content_Type,developer.getMemberID(),TopicId)
        apiService.studentLoginCheck(tag , email, password)
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(com.landtanin.studentattendancecheck.util.Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(com.landtanin.studentattendancecheck.util.Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<StudentModuleCollectionDao>() {
                    @Override
                    public void call(StudentModuleCollectionDao response) {

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
//                        realm.deleteAll(); // clear the current data before load new data
                        realm.delete(StudentLoginStatusDao.class); // delete only data of a specific class
                        realm.copyToRealmOrUpdate(response.getStudentsLogin());
                        realm.commitTransaction();

                        Log.d("getLogin", "login success");

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());

                    }
                });


    }

    // dump data into Realm
    private void getStudent(String student_id){

        //TODO: delete fake id
        String studentId = student_id;

        ApiService apiService = HttpManager.getInstance().create(ApiService.class);
//        apiService.loadStudentModule(Authorization,Content_Type,developer.getMemberID(),TopicId)
        apiService.loadStudentModule("heyhey", Integer.parseInt(studentId))
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(com.landtanin.studentattendancecheck.util.Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(com.landtanin.studentattendancecheck.util.Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<StudentModuleCollectionDao>() {
                    @Override
                    public void call(StudentModuleCollectionDao response) {

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
//                        realm.deleteAll(); // clear the current data before load new data
                        realm.delete(StudentModuleDao.class); // delete only data of a specific class
                        realm.copyToRealmOrUpdate(response.getData());
                        realm.commitTransaction();

                        Log.d("getStudent", "call success");

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        com.landtanin.studentattendancecheck.util.Utils.getInstance().onHoneyToast(throwable.getLocalizedMessage());

                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
