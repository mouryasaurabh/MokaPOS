package com.source.net.mokashoppincart.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.models.AllItemModel;

import java.util.ArrayList;
import java.util.Random;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Viewholder>{
    private RequestManager glide;
    private ArrayList<AllItemModel> modelArrayList;
    private Context mContext;
    private ItemClick itemClickListener;

    public ItemAdapter(Context context, ArrayList<AllItemModel> modelArrayList, ItemClick itemClickListener){
        this.modelArrayList=modelArrayList;
        mContext=context;
        glide=Glide.with(mContext);
        this.itemClickListener=itemClickListener;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.Viewholder holder, int position) {
        String url=modelArrayList.get(position).getThumbUrl();

        glide.load(url).override(convertDpToPixel(20), convertDpToPixel(20)).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.mImage);

        holder.mTitle.setText(modelArrayList.get(position).getTitle());
        String amount=getAmount(modelArrayList.get(position).getId());
        holder.mPrice.setText("Rs."+amount);
        holder.setOnclickViewItem(modelArrayList.get(position),amount);

    }

    private int convertDpToPixel(float dp){
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }




    private String getAmount(int id) {
        Random rand = new Random();
        int value = rand.nextInt(50);
        return Integer.toString(id*value);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTitle, mPrice;
        private ImageView mImage;
        private View mItemView;

        public Viewholder(View itemView) {
            super(itemView);
            mItemView=itemView;
            mTitle = itemView.findViewById(R.id.item_title);
            mPrice = itemView.findViewById(R.id.item_amount);
            mImage = itemView.findViewById(R.id.item_image);
        }

        private void setOnclickViewItem(final AllItemModel allItemModel, final String amount) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(allItemModel, amount);
                }
            });
        }
    }

    public interface ItemClick{
        void onItemClick(AllItemModel model, String amount);
    }
}
