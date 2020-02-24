package com.gahee.likeordislike;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ButtonViewModel extends ViewModel {


    private MutableLiveData<ButtonStatus> likeButtonStatus = new MutableLiveData<>();
    private MutableLiveData<ButtonStatus> dislikeButtonStatus = new MutableLiveData<>();

    //초기 상태는 눌러지지 않은 상태로 초기화 합니다.
    public MutableLiveData<ButtonStatus> getLikeButtonStatus() {
        likeButtonStatus.setValue(ButtonStatus.UNPRESSED);
        return likeButtonStatus;
    }

    public MutableLiveData<ButtonStatus> getDislikeButtonStatus() {
        dislikeButtonStatus.setValue(ButtonStatus.UNPRESSED);
        return dislikeButtonStatus;
    }
}

