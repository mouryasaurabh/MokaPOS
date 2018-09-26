package com.source.net.mokashoppincart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.source.net.mokashoppincart.FragmentToActivityInterface;
import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.models.AllItemModel;

import java.util.ArrayList;


public class DiscountFragment extends Fragment {
    private RecyclerView recyclerView;


    public DiscountFragment() {
        // Required empty public constructor
    }

    public static DiscountFragment newInstance() {
        DiscountFragment fragment = new DiscountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.discount_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        recyclerView= view.findViewById(R.id.recycer_view_items);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        ArrayList<String> titleList=new ArrayList<>();
        titleList.add(getString(R.string.discount_a));
        titleList.add(getString(R.string.discount_b));
        titleList.add(getString(R.string.discount_c));
        titleList.add(getString(R.string.discount_d));
        titleList.add(getString(R.string.discount_e));

        ArrayList<String> discountList=new ArrayList<>();
        discountList.add(getString(R.string.discount_a_per));
        discountList.add(getString(R.string.discount_b_per));
        discountList.add(getString(R.string.discount_c_per));
        discountList.add(getString(R.string.discount_d_per));
        discountList.add(getString(R.string.discount_e_per));

        DiscountAdapter itemAdapter=new DiscountAdapter(getActivity(),titleList,discountList);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
