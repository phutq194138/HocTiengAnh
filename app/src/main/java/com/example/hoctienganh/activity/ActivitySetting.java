package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;

public class ActivitySetting extends AppCompatActivity {

    private CheckBox cbMusic, cbEffect;
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        cbMusic = this.findViewById(R.id.cb_music);
        cbEffect = this.findViewById(R.id.cb_effect);
        imgBack = this.findViewById(R.id.img_back_setting);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        loadData();
        cbMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = MyData.sharedPreferences.edit();
                if (isChecked){
                    editor.putBoolean(MyData.HAVE_SOUND, true);
                    MyData.soundBackground.pause();
                    MyData.soundBackground = null;
                    MyData.soundBackground = MyData.nhacnenCo;
                    MyData.soundBackground.start();
                }
                else {
                    editor.putBoolean(MyData.HAVE_SOUND, false);
                    MyData.soundBackground.pause();
                    MyData.soundBackground = null;
                    MyData.soundBackground = MyData.nhacnenKhong;
                }
                editor.commit();
            }
        });

        cbEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = MyData.sharedPreferences.edit();
                if (isChecked){
                    editor.putBoolean(MyData.HAVE_EFFECT, true);
                    MyData.soundEffect.pause();
                    MyData.soundEffect = null;
                    MyData.soundEffect = MyData.hieuungCo;
                }
                else {
                    editor.putBoolean(MyData.HAVE_EFFECT, false);
                    MyData.soundEffect.pause();
                    MyData.soundEffect = null;
                    MyData.soundEffect = MyData.hieuungKhong;
                }
                editor.commit();
            }
        });


        /*switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MyData.media.start();
                }
                else {
                    MyData.media.pause();
                }
            }
        });*/
    }

    private void loadData(){
        Boolean isSound = MyData.sharedPreferences.getBoolean(MyData.HAVE_SOUND, true);
        Boolean isEffect = MyData.sharedPreferences.getBoolean(MyData.HAVE_EFFECT, true);

        if (isSound){
            cbMusic.setChecked(true);
        }
        else {
            cbMusic.setChecked(false);
        }

        if (isEffect){
            cbEffect.setChecked(true);
        }
        else {
            cbEffect.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }

}