package com.ileeric.ksa.sunwoods.ui.pathInput;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PathInputViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PathInputViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}