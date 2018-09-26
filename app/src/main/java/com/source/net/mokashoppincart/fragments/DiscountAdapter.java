package com.source.net.mokashoppincart.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.source.net.mokashoppincart.R;

import java.util.ArrayList;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.Viewholder>{
    private ArrayList<String> discountList,titleList;
    private Context mContext;

    public DiscountAdapter(Context context, ArrayList<String> titleList, ArrayList<String> discountList){
        mContext=context;
        this.titleList=titleList;
        this.discountList=discountList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discountitem_view,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder ;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountAdapter.Viewholder holder, int position) {

        holder.mTitle.setText(titleList.get(position));
        holder.mPrice.setText(discountList.get(position));

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTitle, mPrice;

        public Viewholder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_title);
            mPrice = itemView.findViewById(R.id.item_amount);
        }
    }
}
