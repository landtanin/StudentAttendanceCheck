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

//    private RealmResults<StudentModuleDao> mtimeTableItemList;
    Context mContext;
    ListItemTimeTableBinding b;
    int redColor, greenColor, greyColor, indegoColor;
//    int[] endedModule = new int[5]; // TODO create MaximumEndedModulePerday variable


    public TimeTableListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<StudentModuleDao> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
        mContext = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        redColor = ContextCompat.getColor(parent.getContext(), R.color.colorPink100);
        greenColor = ContextCompat.getColor(parent.getContext(), R.color.colorGreen100);
        greyColor = ContextCompat.getColor(parent.getContext(), R.color.colorGrey100);
        indegoColor = ContextCompat.getColor(parent.getContext(), R.color.colorIndigo50);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_table, parent, false);
        return new RecyclerViewHolder(itemView);

//        b = DataBindingUtil.inflate(inflater, R.layout
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

//        if (endedModule.length>0) {

            final RecyclerViewHolder itemHolder = (RecyclerViewHolder) holder; // it needs RecyclerViewHolder, not RecyclerView.ViewHolder
            StudentModuleDao timeTableItem = getData().get(position);

            itemHolder.moduleNameTxt.setText(timeTableItem.getName());
            itemHolder.moduleIdTxt.setText(timeTableItem.getModuleId());
            itemHolder.statusTxt.setText(timeTableItem.getModStatus());

            if (timeTableItem.getModStatus().equals("active")) {
                itemHolder.itemView.setBackgroundColor(greenColor);
            } else if (timeTableItem.getModStatus().equals("inactive")) {
                itemHolder.itemView.setBackgroundColor(indegoColor);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            itemHolder.timeTxt.setText(dateFormat.format(timeTableItem.getCheckInStart()));
//        ต้องหาค่า LatLng

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
