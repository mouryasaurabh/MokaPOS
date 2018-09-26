package com.source.net.mokashoppincart.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.models.AllItemModel;


public class CustomItemSelectDialog  extends Dialog implements android.view.View.OnClickListener {
    private TextView mCancel,mSave,mPlus,mMinus, mTitleText;
    private RadioGroup mRadioGroup;
    private TextInputEditText mEditText;
    private TextInputLayout mEditTextLyt;
    private Context mContext;
    private String amount;
    private  DialogToActivityListener mDialogListener;
    private AllItemModel allItemModel;


    public CustomItemSelectDialog(Context context, AllItemModel model,String amount, DialogToActivityListener dialogListener) {
        super(context);
        mContext=context;
        this.amount=amount;
        this.mDialogListener=dialogListener;
        allItemModel=model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_item);
        initView();
    }

    private void initView() {
        mCancel = (TextView) findViewById(R.id.close_icon);
        mSave = (TextView) findViewById(R.id.save_button);
        mPlus = (TextView) findViewById(R.id.add_icon);
        mMinus = (TextView) findViewById(R.id.minus_icon);
        mTitleText = (TextView) findViewById(R.id.title_text);
        String title=allItemModel.getTitle()+":"+amount;
        mTitleText.setText(title);

        mEditTextLyt = (TextInputLayout) findViewById(R.id.edt_lyt);
        mEditText = (TextInputEditText) findViewById(R.id.quantity_edt);
        mRadioGroup = (RadioGroup) findViewById(R.id.discount_radio_group);

        mEditText.addTextChangedListener(textWatcher);

        mCancel.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mPlus.setOnClickListener(this);
        mMinus.setOnClickListener(this);



    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEditTextLyt.setError(null);

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text=mEditText.getText().toString();
            if(!TextUtils.isEmpty(text) && Integer.parseInt(text)>1000){
                mEditTextLyt.setError(mContext.getString(R.string.quantity_thousand_error));
            }
        }
    };

    @Override
    public void onClick(View v) {
        String text;
        switch (v.getId()){
            case R.id.close_icon:
                dismiss();
                break;
            case R.id.save_button:
                proceedSave();

                break;
            case R.id.add_icon:
                text=mEditText.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    int value=Integer.parseInt(text);
                    value++;
                    mEditText.setText(value+"");
                }
                break;
            case R.id.minus_icon:
                text=mEditText.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    if(Integer.parseInt(text)>0){
                        int value=Integer.parseInt(text);
                        value--;
                        mEditText.setText(value+"");
                    }else{
                        mEditTextLyt.setError(mContext.getString(R.string.quantity_zero_error));
                    }
                }
                break;
        }
    }

    private void proceedSave() {
        String quantity=mEditText.getText().toString();
        if(TextUtils.isEmpty(quantity)){
            mEditTextLyt.setError(mContext.getString(R.string.quantity_empty_error));
            return;
        }
        float discount=0f;
        switch(mRadioGroup.getCheckedRadioButtonId()){
            case R.id.discount_a:
                discount=0f;
                break;
            case R.id.discount_b:
                discount=10f;
                break;
            case R.id.discount_c:
                discount=35.5f;
                break;
            case R.id.discount_d:
                discount=50f;
                break;
            case R.id.discount_e:
                discount=100f;
                break;
        }
        if(Integer.parseInt(quantity)!=0)
            mDialogListener.onSaveClick(allItemModel, amount, discount,quantity);
        dismiss();
    }

    public interface DialogToActivityListener{
        void onSaveClick(AllItemModel allItemModel, String amount, float discount, String quantity);
    }
}