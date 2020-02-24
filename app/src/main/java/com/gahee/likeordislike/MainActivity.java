package com.gahee.likeordislike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tvLikeCount;
    private TextView tvDislikeCount;
    private ImageButton btnLike;
    private ImageButton btnDislike;

    private static int likeCount = 1, dislikeCount = 1;

    private static boolean isBothPressed = false;

    private MutableLiveData<ButtonStatus> likeButtonStatus = new MutableLiveData<>();
    private MutableLiveData<ButtonStatus> dislikeButtonStatus = new MutableLiveData<>();

    private ButtonViewModel buttonViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvLikeCount = findViewById(R.id.tv_like_count);
        tvDislikeCount = findViewById(R.id.tv_dislike_count);
        btnLike = findViewById(R.id.btn_like);
        btnDislike = findViewById(R.id.btn_dislike);

        tvLikeCount.setText(String.valueOf(likeCount));
        tvDislikeCount.setText(String.valueOf(dislikeCount));

        buttonViewModel = ViewModelProviders.of(this).get(ButtonViewModel.class);
        likeButtonStatus = buttonViewModel.getLikeButtonStatus();
        dislikeButtonStatus = buttonViewModel.getDislikeButtonStatus();


        observeViewModel();
    }



    private void observeViewModel(){

        buttonViewModel.getLikeButtonStatus().observe(this, new Observer<ButtonStatus>() {
            @Override
            public void onChanged(ButtonStatus buttonStatus) {
                switch (buttonStatus){
                    case PRESSED:
                        setLikeCountText(++likeCount);
                        btnLike.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_yellow_24dp));

                        if(dislikeButtonStatus.getValue() == ButtonStatus.PRESSED) {
                            dislikeButtonStatus.setValue(ButtonStatus.UNPRESSED);
                        }
                        break;

                    case UNPRESSED:
                        btnLike.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_black_24dp));
                        //if both are not pressed
                        setLikeCountText(--likeCount);
                        break;

                }
            }
        });

        buttonViewModel.getDislikeButtonStatus().observe(this, new Observer<ButtonStatus>() {
            @Override
            public void onChanged(ButtonStatus buttonStatus) {
                switch (buttonStatus){
                    case PRESSED:
                        setDislikeCountText(++dislikeCount);

                        btnDislike.setImageDrawable(getDrawable(R.drawable.ic_thumb_down_yellow_24dp));

                        if(likeButtonStatus.getValue() == ButtonStatus.PRESSED) {
                            likeButtonStatus.setValue(ButtonStatus.UNPRESSED);
                        }
                        break;

                    case UNPRESSED:
                        btnDislike.setImageDrawable(getDrawable(R.drawable.ic_thumb_down_black_24dp));
                        //if both are not pressed
                        setDislikeCountText(--dislikeCount);
                        break;
                }

            }
        });
    }


    //on button click, change the status of the button with enum ButtonStatus class.
    public void onLikeButtonClicked(View view) {
        if(likeButtonStatus.getValue() == ButtonStatus.UNPRESSED){
            likeButtonStatus.setValue(ButtonStatus.PRESSED);
        }else{
            likeButtonStatus.setValue(ButtonStatus.UNPRESSED);
        }
        Log.d(TAG, "onLikeButtonClicked: " + likeButtonStatus.getValue().toString());
    }


    public void onDislikeButtonClicked(View view) {
        if(dislikeButtonStatus.getValue() == ButtonStatus.UNPRESSED){
            dislikeButtonStatus.setValue(ButtonStatus.PRESSED);
        }else{
            dislikeButtonStatus.setValue(ButtonStatus.UNPRESSED);
        }
        Log.d(TAG, "onDislikeButtonClicked: " + dislikeButtonStatus.getValue().toString());
    }


    private void setLikeCountText(int count){
        tvLikeCount.setText(String.valueOf(count));
    }

    private void setDislikeCountText(int count){
        tvDislikeCount.setText(String.valueOf(count));
    }

}
