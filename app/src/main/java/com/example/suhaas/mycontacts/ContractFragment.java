package com.example.suhaas.mycontacts;

import android.app.Activity;
import android.app.Fragment;

public class ContractFragment<T> extends Fragment{

    private T mContract;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mContract = (T) getActivity();
        }catch (ClassCastException e){
            throw new IllegalStateException("Activity does not implement contract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContract = null;
    }

    public T getContract(){
        return mContract;
    }
}
