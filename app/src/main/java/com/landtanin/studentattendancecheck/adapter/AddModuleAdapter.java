package com.landtanin.studentattendancecheck.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.landtanin.studentattendancecheck.R;

/**
 * Created by landtanin on 2/15/2017 AD.
 */

public class AddModuleAdapter extends RecyclerView.Adapter<AddModuleAdapter.RecyclerViewHolder> {
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_module, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.addModuleModuleNameTxt.setText("Module" + position);
        holder.addModuleModuleIdTxt.setText("00000" + position);
        holder.addModuleCheckBox.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return 10;
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
