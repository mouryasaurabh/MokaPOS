package com.source.net.mokashoppincart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.source.net.mokashoppincart.AppConstants;
import com.source.net.mokashoppincart.FragmentToActivityInterface;
import com.source.net.mokashoppincart.R;


public class LibraryFragment extends Fragment implements View.OnClickListener{
    private FragmentToActivityInterface mListener;
    private RelativeLayout mAllItemsLyt, mDiscountLyt;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mAllItemsLyt=view.findViewById(R.id.all_items_lyt);
        mDiscountLyt=view.findViewById(R.id.discount_lyt);

        mAllItemsLyt.setOnClickListener(this);
        mDiscountLyt.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInterface) {
            mListener = (FragmentToActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_items_lyt:
                mListener.replaceFragment(AppConstants.FRAGMENT_ALL_ITEMS);
                break;

            case R.id.discount_lyt:
                mListener.replaceFragment(AppConstants.FRAGMENT_DISCOUNT);
                break;
        }
    }
}
