package com.source.net.mokashoppincart;

import com.source.net.mokashoppincart.models.AllItemModel;

public interface FragmentToActivityInterface {
    void replaceFragment(int fragmentCode);
    void onItemClick(AllItemModel model, String amount);
}
