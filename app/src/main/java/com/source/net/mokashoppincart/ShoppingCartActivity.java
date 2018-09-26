package com.source.net.mokashoppincart;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.source.net.mokashoppincart.fragments.AllItemsFragment;
import com.source.net.mokashoppincart.fragments.CustomItemSelectDialog;
import com.source.net.mokashoppincart.fragments.DiscountFragment;
import com.source.net.mokashoppincart.fragments.LibraryFragment;
import com.source.net.mokashoppincart.fragments.ShoppingCartFragment;
import com.source.net.mokashoppincart.models.AllItemModel;

public class ShoppingCartActivity extends AppCompatActivity implements FragmentToActivityInterface{
    private ShoppingCartFragment mShopingFragment;
    private TextView mTextView;
    private CustomItemSelectDialog customItemSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initView();

    }

    private void initView() {
        mTextView=findViewById(R.id.title_text);
        addFragment();
        replaceFragment(AppConstants.FRAGMENT_LIBRARY);
    }

    private void addFragment() {
        mShopingFragment= ShoppingCartFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_static, mShopingFragment,AppConstants.TAG_FRAGMENT_SHOPPING_CART);
        transaction.commit();

    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 2 )
            mTextView.setText(getString(R.string.library));

        if (getFragmentManager().getBackStackEntryCount() <= 1 ) {

            finish();
        } else {
            super.onBackPressed();
        }
    }


    private void replaceFragmentMethod(Fragment fragment,String tag){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void replaceFragment(int fragmentCode) {
        Fragment fragment;
        String tag;
        switch (fragmentCode){
            case AppConstants.FRAGMENT_LIBRARY:
                fragment= LibraryFragment.newInstance();
                tag=AppConstants.TAG_FRAGMENT_LIBRARY;
                mTextView.setText(getString(R.string.library));
                break;
            case AppConstants.FRAGMENT_ALL_ITEMS:
                fragment= AllItemsFragment.newInstance();
                tag=AppConstants.TAG_FRAGMENT_ALL_ITEMS;
                mTextView.setText(getString(R.string.all_items));
                break;
            case AppConstants.FRAGMENT_DISCOUNT:
                fragment= DiscountFragment.newInstance();
                tag=AppConstants.TAG_FRAGMENT_DISCOUNT;
                mTextView.setText(getString(R.string.all_discount));
                break;
            default:
                fragment= LibraryFragment.newInstance();
                tag=AppConstants.TAG_FRAGMENT_LIBRARY;
                mTextView.setText(getString(R.string.library));
                break;
        }
        replaceFragmentMethod(fragment,tag);
    }

    @Override
    public void onItemClick(AllItemModel model, String amount) {
        customItemSelectDialog=new CustomItemSelectDialog(this,model,amount,dialogToActivityListener);
        customItemSelectDialog.setCancelable(true);
        customItemSelectDialog.show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(customItemSelectDialog!=null && customItemSelectDialog.isShowing())
            customItemSelectDialog.dismiss();
    }

    private CustomItemSelectDialog.DialogToActivityListener dialogToActivityListener=new CustomItemSelectDialog.DialogToActivityListener() {
        @Override
        public void onSaveClick(AllItemModel allItemModel, String amount, float discount, String quantity) {
            if(mShopingFragment!=null){
                mShopingFragment.updateValues(allItemModel, amount, discount,quantity);
            }
                 }
    };
}

