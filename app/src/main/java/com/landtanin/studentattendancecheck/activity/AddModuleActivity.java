package com.landtanin.studentattendancecheck.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.adapter.AddModuleAdapter;
import com.landtanin.studentattendancecheck.adapter.AddModuleItem;
import com.landtanin.studentattendancecheck.databinding.ActivityAddModuleBinding;

import java.util.ArrayList;
import java.util.List;

public class AddModuleActivity extends AppCompatActivity {
    
//    RecyclerView rv;
    AddModuleAdapter mAddModuleAdapter;
    List<AddModuleItem> mModuleItemList = new ArrayList<>();
    ActivityAddModuleBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_add_module);
        
        initInstance();
        
    }

    private void initInstance() {

        // **** toolbar
        setSupportActionBar(b.addModuleActivityToolbar);

        // **** recyclerview
        StaggeredGridLayoutManager rvLayoutManager = new StaggeredGridLayoutManager(1, 1);

        b.rvAddModule.setLayoutManager(rvLayoutManager);
        mAddModuleAdapter = new AddModuleAdapter(mModuleItemList, this);

        b.rvAddModule.setAdapter(mAddModuleAdapter);
        b.rvAddModule.setHasFixedSize(true);

        connectToDataBase();



    }

    private void connectToDataBase() {

        // hardcoded item to RecyclerView
        for (int i = 0; i < 100; i++) {

            AddModuleItem addModuleItem = new AddModuleItem("item " + i, "item2 " + i, false);
            mModuleItemList.add(addModuleItem);

        }

        mAddModuleAdapter.notifyDataSetChanged();

    }


}
