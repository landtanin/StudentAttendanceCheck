package com.landtanin.studentattendancecheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.landtanin.studentattendancecheck.R;

import java.util.List;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class AddModuleAdapter extends RecyclerView.Adapter<AddModuleAdapter.RecyclerViewHolder> {

    private List<AddModuleItem> mModuleItemList;
    Context mContext;

    public AddModuleAdapter(List<AddModuleItem> moduleItemList, Context context) {
        mModuleItemList = moduleItemList;
        mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_module, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        AddModuleItem addModuleItem = mModuleItemList.get(position);

        holder.addModuleModuleNameTxt.setText(addModuleItem.getModuleText());
        holder.addModuleModuleIdTxt.setText(addModuleItem.getModuleId());
        holder.addModuleCheckBox.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView addModuleModuleNameTxt;
        TextView addModuleModuleIdTxt;
        CheckBox addModuleCheckBox;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            addModuleModuleNameTxt = (TextView) itemView.findViewById(R.id.addModuleModuleNameTxt);
            addModuleModuleIdTxt = (TextView) itemView.findViewById(R.id.addModuleModuleIdTxt);
            addModuleCheckBox = (CheckBox) itemView.findViewById(R.id.addModuleCheckbox);

        }
    }
}
