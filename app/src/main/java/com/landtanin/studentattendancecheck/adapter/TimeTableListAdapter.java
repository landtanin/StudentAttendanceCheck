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

    private List<AddModuleItem> mModuleItemList;
    Context mContext;

    // TODO: Constructor

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_table, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        AddModuleItem addModuleItem = mModuleItemList.get(position);

//        holder.addModuleModuleNameTxt.setText(addModuleItem.getModuleText());
//        holder.addModuleModuleIdTxt.setText(addModuleItem.getModuleId());
//        holder.addModuleCheckBox.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return 100;
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView moduleNameTxt, moduleIdTxt, statusTxt, timeTxt, locationTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            moduleNameTxt = (TextView) itemView.findViewById(R.id.moduleIdTxt);
            moduleIdTxt = (TextView) itemView.findViewById(R.id.moduleIdTxt);
            statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            timeTxt = (TextView) itemView.findViewById(R.id.timeTxt);
            locationTxt = (TextView) itemView.findViewById(R.id.locationTxt);

        }
    }
}
