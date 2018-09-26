package com.source.net.mokashoppincart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.source.net.mokashoppincart.FragmentToActivityInterface;
import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.ShoppingCartActivity;
import com.source.net.mokashoppincart.models.AllItemModel;


public class ShoppingCartFragment extends Fragment implements View.OnClickListener{
    private TextView mClearSale,mCharge;
    private RecyclerView mCartRv;
    private CartItemAdapter cartItemAdapter;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mClearSale=view.findViewById(R.id.clear_sale);
        mCharge=view.findViewById(R.id.charge);
        mCartRv=view.findViewById(R.id.cart_item_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mCartRv.setLayoutManager(llm);

        cartItemAdapter=new CartItemAdapter(getActivity(),amountChangeListener);
        mCartRv.setAdapter(cartItemAdapter);
        mCartRv.setNestedScrollingEnabled(false);

        mCharge.setText(String.format(getString(R.string.charge_amount),"0"));
        mClearSale.setOnClickListener(this);
        mCharge.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void updateValues(AllItemModel allItemModel, String amount, float discount,String quantity) {
        if(cartItemAdapter!=null){
            cartItemAdapter.updateData(allItemModel,  amount,  discount, quantity);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.charge:
                Toast.makeText(getActivity(),getString(R.string.bill_generated_successfully), Toast.LENGTH_LONG).show();
                break;
            case R.id.clear_sale:
                onClearSaleClick();
                break;
        }

    }

    private void onClearSaleClick() {
        if(cartItemAdapter!=null){
            cartItemAdapter.onClearSale();
        }
        mCharge.setText(String.format(getString(R.string.charge_amount),"0"));
    }

    private CartItemAdapter.AmountChangeListener amountChangeListener=new CartItemAdapter.AmountChangeListener(){
        @Override
        public void onAmountChange(String amount) {
            mCharge.setText(String.format(getString(R.string.charge_amount),amount));
        }
    };
}
