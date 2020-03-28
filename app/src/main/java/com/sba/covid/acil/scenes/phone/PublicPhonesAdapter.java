/*
 *  * Created by Serhat Bekir AK on 26.03.2020 23:11
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 23:11
 */

package com.sba.covid.acil.scenes.phone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sba.covid.acil.R;
import com.sba.covid.acil.api.model.main.MainPhones;
import com.sba.covid.acil.helpers.core.MyFragmentManager;
import com.sba.covid.acil.helpers.utilities.Utilities;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicPhonesAdapter extends RecyclerView.Adapter {

    private Context context;
    private MyFragmentManager myFragmentManager;
    private ArrayList<MainPhones.Phone> publicPhoneList;

    public PublicPhonesAdapter(Context context, MyFragmentManager myFragmentManager, ArrayList<MainPhones.Phone> dataSet) {
        this.context = context;
        this.myFragmentManager = myFragmentManager;
        this.publicPhoneList = dataSet;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyHolder vh;
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_phones, parent, false);
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
        return publicPhoneList.size();
    }

    void favView(final MyHolder vh, final int position) {
        MainPhones.Phone item = publicPhoneList.get(position);
        vh.phone.setText(item.getPhone());
        vh.title.setText(item.getName());
        vh.content.setText(item.getCategory().getName());
        vh.parent.setOnClickListener((View v) -> Utilities.callPhone(context, item.getPhone()));
    }

    public class MyHolder extends RecyclerView.ViewHolder {

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
