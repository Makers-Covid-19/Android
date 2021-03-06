/*
 *  * Created by Serhat Bekir AK on 26.03.2020 23:11
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 26.03.2020 23:11
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
import com.mobilegion.acil.numaralar.helpers.utilities.Utilities;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicPhonesAdapter extends RecyclerView.Adapter {

    private Context context;
    private myOnClick myOnClick;
    private ArrayList<MainPhones.Phone> publicPhoneList;

    public PublicPhonesAdapter(Context context, myOnClick myOnClick, ArrayList<MainPhones.Phone> dataSet) {
        this.context = context;
        this.myOnClick = myOnClick;
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
        vh.misreporting.setOnClickListener((View v) -> Utilities.Misreporting(context,item.getId(),item.getPhone(),myOnClick));
        vh.parent.setOnLongClickListener((View v) -> Utilities.clipboardCopy(context,item.getPhone()));
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.content) TextView content;
        @BindView(R.id.parent) RelativeLayout parent;
        @BindView(R.id.misreporting) ImageView misreporting;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
