package com.source.net.mokashoppincart.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.models.AllItemModel;
import com.source.net.mokashoppincart.models.CartItemModel;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.DiscountViewHolder>{
    private ArrayList<CartItemModel> cartItemModelList=new ArrayList<>();
    private Context mContext;
    private AmountChangeListener mAmountChangeListener;

    public interface AmountChangeListener{
        void onAmountChange(String amount);
    }

    public CartItemAdapter(Context context, AmountChangeListener amountChangeListener){
        mContext=context;
        mAmountChangeListener=amountChangeListener;
    }

    public void updateData( AllItemModel allItemModel, String amount, float discount,String quantity){
        int positionExist=idExist(allItemModel,Float.valueOf(discount));
        if(positionExist!=-1){
            CartItemModel cartItemModel=cartItemModelList.get(positionExist);
            int quantityTotal=cartItemModel.getQuantity()+Integer.parseInt(quantity);
            cartItemModel.setQuantity(quantityTotal);
            cartItemModelList.set(positionExist,cartItemModel);
            notifyItemChanged(positionExist);
        }else{
            CartItemModel cartItemModel=new CartItemModel();
            cartItemModel.setId(allItemModel.getId());
            cartItemModel.setDiscount(discount);
            cartItemModel.setItemCost(amount);
            cartItemModel.setQuantity(Integer.parseInt(quantity));
            cartItemModel.setTitle(allItemModel.getTitle());
            cartItemModelList.add(cartItemModel);
            notifyDataSetChanged();;
        }
        mAmountChangeListener.onAmountChange(calculateTotalBill());
    }

    public void onClearSale(){
        cartItemModelList.clear();
        notifyDataSetChanged();
    }

    private int idExist(AllItemModel allItemModel,float discount) {
        if(cartItemModelList==null||cartItemModelList.size()==0){
            return -1;
        }else{
            for(CartItemModel cartItemModel:cartItemModelList){
                if(cartItemModel.getId()==allItemModel.getId() && Float.compare(discount, cartItemModel.getDiscount())==0){
                    return cartItemModelList.indexOf(cartItemModel);
                }
            }
            return -1;
        }
    }


    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.DiscountViewHolder holder, int position) {

        if(position==cartItemModelList.size()){
            holder.heading.setText(mContext.getString(R.string.subtotal));
            holder.quantity.setText("");
            holder.amount.setText("Rs."+getTotalAmount());
        }else if(position==cartItemModelList.size()+1){
            holder.heading.setText(mContext.getString(R.string.discount));
            holder.quantity.setText("");
            holder.amount.setText("(Rs."+getTotalDiscount()+")");
        }else{
            holder.heading.setText(cartItemModelList.get(position).getTitle());
            holder.quantity.setText("X"+cartItemModelList.get(position).getQuantity());
            holder.amount.setText("Rs."+cartItemModelList.get(position).getItemCost());
        }


    }

    private int getTotalAmount(){
        if(cartItemModelList==null||cartItemModelList.size()==0)
            return 0;
        int cost=0;
        for(CartItemModel cartItemModel: cartItemModelList){
            int quant= cartItemModel.getQuantity();
            int price=Integer.parseInt(cartItemModel.getItemCost());
            cost+=quant*price;
        }
        return cost;

    }

    private float getTotalDiscount(){
        if(cartItemModelList==null||cartItemModelList.size()==0)
            return 0;
        float discountAmount=0;
        for(CartItemModel cartItemModel: cartItemModelList){
            int quant= cartItemModel.getQuantity();
            int price=Integer.parseInt(cartItemModel.getItemCost());
            float discount=cartItemModel.getDiscount();

            discountAmount+=(quant*price*discount)/100;
        }
        return discountAmount;

    }

    public String calculateTotalBill(){
        if(cartItemModelList==null||cartItemModelList.size()==0){
            return "0";
        }
        float bill=getTotalAmount()-getTotalDiscount();
        return bill+"";
    }

    @Override
    public int getItemCount() {
        if(cartItemModelList==null||cartItemModelList.size()==0)
            return 0;
        else
            return cartItemModelList.size()+2;
    }

    public class DiscountViewHolder extends RecyclerView.ViewHolder {
        private TextView amount,heading,quantity;

        public DiscountViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.subtotal_item);
            quantity = itemView.findViewById(R.id.quantity_item);
            amount = itemView.findViewById(R.id.amount_item);
        }
    }
}
