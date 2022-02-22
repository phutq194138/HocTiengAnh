package com.example.hoctienganh.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.activity.ChooseTopic;
import com.example.hoctienganh.activity.ChooseVideo;
import com.example.hoctienganh.activity.ListenAndChoose;
import com.example.hoctienganh.activity.SortCharacter;
import com.example.hoctienganh.activity.WatchAndChoose;
import com.example.hoctienganh.activity.WatchAndWrite;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Button btnWaC, btnLaC, btnWaW, btnVideo, btnVocabulary, btnSort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MyData.soundBackground.start();

        initUi();
        return mView;
    }

    private void initUi(){
        btnWaC = mView.findViewById(R.id.btn_watch_and_choose);
        btnLaC = mView.findViewById(R.id.btn_listen_and_choose);
        btnVocabulary = mView.findViewById(R.id.btn_vocabulary);
        btnWaW = mView.findViewById(R.id.btn_watch_and_write);
        btnVideo = mView.findViewById(R.id.btn_video);
        btnSort = mView.findViewById(R.id.btn_sort);

        btnVocabulary.setOnClickListener(this);
        btnWaC.setOnClickListener(this);
        btnLaC.setOnClickListener(this);
        btnWaW.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.btn_vocabulary:
                Intent intentVocabulary = new Intent(getContext(), ChooseTopic.class);
                startActivity(intentVocabulary);
                break;
            case R.id.btn_watch_and_choose:
                Intent intentWatchAndChoose = new Intent(getContext(), WatchAndChoose.class);
                startActivity(intentWatchAndChoose);
                break;
            case R.id.btn_listen_and_choose:
                Intent intentListenAndChoose = new Intent(getContext(), ListenAndChoose.class);
                startActivity(intentListenAndChoose);
                break;
            case R.id.btn_watch_and_write:
                Intent intentWatchAndWrite = new Intent(getContext(), WatchAndWrite.class);
                startActivity(intentWatchAndWrite);
                break;
            case R.id.btn_video:
                Intent intentVideo = new Intent(getContext(), ChooseVideo.class);
                startActivity(intentVideo);
                break;
            case R.id.btn_sort:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    public void onStart() {
        MyData.soundBackground.start();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }

}
