package com.ileeric.ksa.sunwoods.ui.dashboard;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HyungtamViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HyungtamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}