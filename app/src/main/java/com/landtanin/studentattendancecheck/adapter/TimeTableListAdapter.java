package com.landtanin.studentattendancecheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.landtanin.studentattendancecheck.R;
import com.landtanin.studentattendancecheck.dao.StudentModuleDao;
import com.landtanin.studentattendancecheck.databinding.ListItemTimeTableBinding;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class TimeTableListAdapter extends RealmRecyclerViewAdapter<StudentModuleDao,RecyclerView.ViewHolder>{

    Context mContext;
    int redColor, greenColor, greyColor, indegoColor, statusTxtGreenColor, blackColor;

    public TimeTableListAdapter(@NonNull Context context,
                                @Nullable OrderedRealmCollection<StudentModuleDao> data,
                                boolean autoUpdate) {
        super(context, data, autoUpdate);
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        redColor = ContextCompat.getColor(parent.getContext(), R.color.colorPink100);
        greenColor = ContextCompat.getColor(parent.getContext(), R.color.colorRealGreen100);
        statusTxtGreenColor = ContextCompat.getColor(parent.getContext(), R.color.colorActiveStatus);
        greyColor = ContextCompat.getColor(parent.getContext(), R.color.colorGrey500);
        indegoColor = ContextCompat.getColor(parent.getContext(), R.color.colorIndigo50);
        blackColor = ContextCompat.getColor(parent.getContext(), android.R.color.black);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_table, parent, false);
        return new RecyclerViewHolder(itemView);

//        b = DataBindingUtil.inflate(inflater, R.layout
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if (endedModule.length>0) {

            RecyclerViewHolder itemHolder = (RecyclerViewHolder) holder; // it needs RecyclerViewHolder, not RecyclerView.ViewHolder
            StudentModuleDao timeTableItem = getData().get(position);

            itemHolder.moduleNameTxt.setText(timeTableItem.getName());
            itemHolder.moduleIdTxt.setText(timeTableItem.getModuleId());
            itemHolder.statusTxt.setText(timeTableItem.getModStatus());

            if (timeTableItem.getModStatus().equals("active")) {
                itemHolder.itemView.setBackgroundColor(greenColor);
                itemHolder.statusTxt.setTextColor(statusTxtGreenColor);
            } else if (timeTableItem.getModStatus().equals("inactive")) {
                itemHolder.itemView.setBackgroundColor(indegoColor);
                itemHolder.statusTxt.setTextColor(blackColor);
            } else if (timeTableItem.getModStatus().equals("no more class")) {
                itemHolder.itemView.setBackgroundColor(greyColor);
                itemHolder.statusTxt.setTextColor(blackColor);
            }


            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
            String timeStr = currentTimeFormat.format(timeTableItem.getStartDate())
                + " - " + currentTimeFormat.format(timeTableItem.getEndDate());
            itemHolder.timeTxt.setText(timeStr);

            itemHolder.locationTxt.setText(timeTableItem.getRoom());

//        }

    }

    @Override
    public int getItemCount() {

//        int deductAmount = 0;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date now = Calendar.getInstance().getTime();
//
//        for (int i = 0; i < getData().size(); i++) {
//
//            if (!(((dateFormat.format(getData().get(i).getStartDate())
//                    .compareTo(dateFormat.format(now))) <= 0 ) &&
//                    ((dateFormat.format(getData().get(i).getEndDate())
//                    .compareTo(dateFormat.format(now))) > 0 ))) {
//
////                showCurrentStatus(studentModuleDao, targetingModule);
//                endedModule[i] = i;
//                deductAmount++;
//
//                Log.d("endedModule", String.valueOf(endedModule[1]));
//                Log.d("deductAmount", String.valueOf(deductAmount));
//            }
//
//        }

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
