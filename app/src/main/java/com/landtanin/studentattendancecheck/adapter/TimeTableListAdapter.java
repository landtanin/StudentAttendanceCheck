package com.landtanin.studentattendancecheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.ListItemTimeTableBinding;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class TimeTableListAdapter extends RealmRecyclerViewAdapter<StudentModuleDao,RecyclerView.ViewHolder>{

//    private RealmResults<StudentModuleDao> mtimeTableItemList;
    Context mContext;
    ListItemTimeTableBinding b;

    public TimeTableListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<StudentModuleDao> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
        mContext = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_table, parent, false);
        return new RecyclerViewHolder(itemView);

//        b = DataBindingUtil.inflate(inflater, R.layout
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final RecyclerViewHolder itemHolder = (RecyclerViewHolder) holder; // it needs RecyclerViewHolder, not RecyclerView.ViewHolder
        StudentModuleDao timeTableItem = getData().get(position);

        itemHolder.moduleNameTxt.setText(timeTableItem.getName());
        itemHolder.moduleIdTxt.setText(timeTableItem.getId());
        itemHolder.statusTxt.setText(timeTableItem.getModStatus());
        itemHolder.timeTxt.setText(timeTableItem.getDay());
//        ต้องหาค่า LatLng

        itemHolder.locationTxt.setText(timeTableItem.getCheckInStart());


    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ListItemTimeTableBinding b;
        TextView moduleNameTxt, moduleIdTxt, statusTxt, timeTxt, locationTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            moduleNameTxt = (TextView) itemView.findViewById(R.id.moduleNameTxt);
            moduleIdTxt = (TextView) itemView.findViewById(R.id.moduleIdTxt);
            statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            timeTxt = (TextView) itemView.findViewById(R.id.timeTxt);
            locationTxt = (TextView) itemView.findViewById(R.id.locationTxt);

        }

//        public RecyclerViewHolder(ListItemTimeTableBinding b) {
//            super(b.getRoot());
//            this.b = b;
//
//        }
    }

}
