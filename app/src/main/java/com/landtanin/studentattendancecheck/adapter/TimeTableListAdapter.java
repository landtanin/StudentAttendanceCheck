package com.landtanin.studentattendancecheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.landtanin.studentattendancecheck.R;

import java.util.List;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class TimeTableListAdapter extends RecyclerView.Adapter<TimeTableListAdapter.RecyclerViewHolder> {

    private List<TimeTableListItem> mtimeTableItemList;
    Context mContext;


    public TimeTableListAdapter(List<TimeTableListItem> moduleItemList, Context context) {
        mtimeTableItemList = moduleItemList;
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_table, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        TimeTableListItem timeTableItem = mtimeTableItemList.get(position);

        holder.moduleNameTxt.setText(timeTableItem.getModuleNameTxt());
        holder.moduleIdTxt.setText(timeTableItem.getModuleIdTxt());
        holder.statusTxt.setText(timeTableItem.getStatusTxt());
        holder.timeTxt.setText(timeTableItem.getTimeTxt());
        holder.locationTxt.setText(timeTableItem.getLocationTxt());

    }

    @Override
    public int getItemCount() {
        return 100;
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView moduleNameTxt, moduleIdTxt, statusTxt, timeTxt, locationTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            moduleNameTxt = (TextView) itemView.findViewById(R.id.moduleNameTxt);
            moduleIdTxt = (TextView) itemView.findViewById(R.id.moduleIdTxt);
            statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            timeTxt = (TextView) itemView.findViewById(R.id.timeTxt);
            locationTxt = (TextView) itemView.findViewById(R.id.locationTxt);

        }
    }
}
