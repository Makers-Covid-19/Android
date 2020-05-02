/*
 *  * Created by Serhat Bekir AK on 26.03.2020 21:14
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 21:14
 */

package com.mobilegion.acil.numaralar.scenes.phone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobilegion.acil.numaralar.R;
import com.mobilegion.acil.numaralar.api.model.main.MainPhones;
import com.mobilegion.acil.numaralar.helpers.core.MyFragmentManager;
import com.mobilegion.acil.numaralar.helpers.utilities.Utilities;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MajorPhonesAdapter extends RecyclerView.Adapter {

    private Context context;
    private MyFragmentManager myFragmentManager;
    private ArrayList<MainPhones.Phone> majorPhoneList;

    public MajorPhonesAdapter(Context context, MyFragmentManager myFragmentManager, ArrayList<MainPhones.Phone> dataSet) {
        this.context = context;
        this.myFragmentManager = myFragmentManager;
        this.majorPhoneList = dataSet;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyHolder vh;
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_important_phones, parent, false);
        vh = new MyHolder(convertView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder vh = (MyHolder) holder;
        favView(vh, position);
    }

    @Override
    public int getItemCount() {
        return majorPhoneList.size();
    }

    void favView(final MyHolder vh, final int position) {
        MainPhones.Phone item = majorPhoneList.get(position);
        vh.image.setImageResource(Utilities.getDrawableByName(context, (int)item.getCategory().getID()));
        vh.phone.setText(item.getPhone());
        vh.title.setText(item.getName());
        vh.content.setText(item.getCategory().getName());
        vh.parent.setOnClickListener((View v) -> Utilities.callPhone(context, item.getPhone()));
        vh.parent.setOnLongClickListener((View v) -> Utilities.clipboardCopy(context,item.getPhone()));
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.content) TextView content;
        @BindView(R.id.parent) RelativeLayout parent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
