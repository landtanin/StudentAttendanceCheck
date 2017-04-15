package com.landtanin.studentattendancecheck.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.activity.MainActivity;
import com.landtanin.studentattendancecheck.dao.StudentModuleCollectionDao;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.dao.User;
import com.landtanin.studentattendancecheck.databinding.FragmentLoginBinding;
import com.landtanin.studentattendancecheck.manager.HttpManager;
import com.landtanin.studentattendancecheck.manager.http.ApiService;
import com.landtanin.studentattendancecheck.util.Utils;

import io.realm.Realm;
import io.realm.RealmResults;
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

                // if success = 1, intent, else, toast fail
                RealmResults<User> user = Realm.getDefaultInstance().where(User.class)
                        .findAll();
                        // alternative from findAll

                Log.w("LOGIN", user.toString());

//                SharedPreferences prefs = getContext().getSharedPreferences("login_state", Context.MODE_PRIVATE);
//                String loginState = prefs.getString("login_state_var", null);

//                while (user.toString().equals("[]")) {
//
//                    Log.w("LOGIN", user.toString());
//                    Log.w("WAIT", "WAIT");
//                    user = Realm.getDefaultInstance().where(User.class)
//                            .findAll();
//
//                }

//                if (loginState.equals("success")) {

//                    retrieve student data and check whether he or she already registered for any module
                    getStudent(user.get(0).getStudentId()); // dump student data of this student_id into Realm

//                    RealmResults<StudentModuleDao> studentModuleDao = Realm.getDefaultInstance().where(StudentModuleDao.class)
//                          .findAll();

//                    Log.w("Mr." + user.get(0).getName() + " first module",
//                            studentModuleDao.get(0).getName());
//                    Log.w("STUDENT", studentModuleDao.toString());

//                    if (studentModuleDao.get(0) != null) {
//
//                        prefs.edit().putString("registered_or_not", "yes");
//
//                    } else {
//
//                        prefs.edit().putString("registered_or_not", "no");
//
//                    }

//                    prefs.edit().apply();

                    Intent intent = new Intent(getActivity(), MainActivity.class);

//                    if (studentModuleDao.get(0) != null) {
//
//                        // he is ...... who study ......
//                        Log.w("he is " + user.get(0).getName() + " who study ",
//                                studentModuleDao.get(0).getName());
//
//                        intent.putExtra("add_or_not", "not_add");
//
//                    } else {
//
//                        intent.putExtra("add_or_not", "add");
//
//                    }

                    startActivity(intent);

//                } else {
//
//                    Utils.getInstance().onHoneyToast("invalid email or password");
//
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
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .subscribe(new Action1<StudentModuleCollectionDao>() {
                    @Override
                    public void call(StudentModuleCollectionDao response) {

                        SharedPreferences prefs = getContext().getSharedPreferences("login_state", Context.MODE_PRIVATE);

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
//                        realm.deleteAll(); // clear the current data before load new data
                        realm.delete(User.class); // delete only data of a specific class

                        if (response.getResult().equals("success")) {

                            SharedPreferences.Editor editor = prefs.edit();
                            // Add/Edit/Delete
                            editor.putString("login_state_var", response.getResult());
                            editor.apply();

                            realm.copyToRealmOrUpdate(response.getUser());
                            realm.commitTransaction();

                        } else {

                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        String sharePrefVar = prefs.getString("login_state_var", null);

                        Log.w("getLogin", response.getResult());
                        Log.w("SharePrefs", sharePrefVar);

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Utils.getInstance().onHoneyToast("LOGIN "+throwable.getLocalizedMessage());

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
                .subscribeOn(Utils.getInstance().defaultSubscribeScheduler())
                .unsubscribeOn(Utils.getInstance().defaultSubscribeScheduler())
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

                        Utils.getInstance().onHoneyToast("STUDENT "+throwable.getLocalizedMessage());

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
