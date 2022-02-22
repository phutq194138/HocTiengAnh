package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.VideoYoutube;
import com.example.hoctienganh.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChooseVideo extends AppCompatActivity {

    private RecyclerView rcvVideo;
    private List<VideoYoutube> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_video);

        ImageView imgBack = this.findViewById(R.id.img_back_choose_video);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        rcvVideo = this.findViewById(R.id.rcv_video);

        VideoAdapter videoAdapter = new VideoAdapter(new VideoAdapter.IClickVideo() {
            @Override
            public void onClickVideo(VideoYoutube videoYoutube) {
                clickVideo(videoYoutube);
            }
        });
        createListVideo();
        videoAdapter.setData(videoList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rcvVideo.setLayoutManager(linearLayoutManager);
        rcvVideo.setAdapter(videoAdapter);
    }

    private void clickVideo(VideoYoutube videoYoutube){
        Intent intentPlayVideo = new Intent(this, PlayVideo.class);
        intentPlayVideo.putExtra("idVideo", videoYoutube.getIdVideo());
        startActivity(intentPlayVideo);
    }

    private void createListVideo(){
        List<VideoYoutube> list = new ArrayList<>();

        list.add(new VideoYoutube("Fruits Song", "f_CYqTpsgkI", R.drawable.video1));
        list.add(new VideoYoutube("Wild Animals", "p5qwOxlvyhk", R.drawable.video2));
        list.add(new VideoYoutube("The ABC Song", "75p-N9YKqNo", R.drawable.video3));
        list.add(new VideoYoutube("Body Song", "AlKXoHvwluA", R.drawable.video4));
        list.add(new VideoYoutube("Vehicle Song", "5-DeiXPJ3H8", R.drawable.video5));
        list.add(new VideoYoutube("Animal Song", "OwRmivbNgQk", R.drawable.video6));
        list.add(new VideoYoutube("Numbers Song", "D0Ajq682yrA", R.drawable.video7));
        list.add(new VideoYoutube("Jobs Song", "ckKQclquAXU", R.drawable.video8));
        list.add(new VideoYoutube("Vegetables Song", "RE5tvaveVak", R.drawable.video9));
        list.add(new VideoYoutube("Vegetable Song", "DOT15xaX7-E", R.drawable.video10));
        list.add(new VideoYoutube("Sports Song", "WCYTlVF-djw", R.drawable.video11));
        list.add(new VideoYoutube("Subject Song", "JoDm0RC5gk8", R.drawable.video12));
        list.add(new VideoYoutube("Subjects Song", "AnZxeX_8mVk", R.drawable.video13));
        list.add(new VideoYoutube("Foods Song", "6IwulRrYnzQ", R.drawable.video14));
        list.add(new VideoYoutube("Study Song", "41cJ0mqWses", R.drawable.video15));

        videoList = list;
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