package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Topic;
import com.example.hoctienganh.adapter.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChooseTopic extends AppCompatActivity {

    private RecyclerView rcvTopic;
    private List<Topic> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic);

        ImageView imgBack = this.findViewById(R.id.img_back_choose_topic);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        rcvTopic = this.findViewById(R.id.rcv_topic);
        createDataTopic();
        TopicAdapter topicAdapter = new TopicAdapter(new TopicAdapter.IClickTopic() {
            @Override
            public void onClickTopic(Topic topic) {
                actionClickTopic(topic);
            }
        });
        topicAdapter.setData(topicList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvTopic.setLayoutManager(gridLayoutManager);

        rcvTopic.setAdapter(topicAdapter);
    }

    private void actionClickTopic(Topic topic){
        Intent intentVocabulary = new Intent(this, Vocabulary.class);
        intentVocabulary.putExtra("topic name", topic.getName());
        startActivity(intentVocabulary);
    }

    private void createDataTopic(){
        List<Topic> list = new ArrayList<>();

        list.add(new Topic("Alphabet", "Chữ cái", R.drawable.alphabet));
        list.add(new Topic("Number", "Chữ số", R.drawable.number));
        list.add(new Topic("Animal", "Động vật", R.drawable.animal));
        list.add(new Topic("Color", "Màu sắc", R.drawable.color));
        list.add(new Topic("Clothes", "Thời trang", R.drawable.clothes));
        list.add(new Topic("Fruit","Trái cây", R.drawable.fruits));
        list.add(new Topic("Drink", "Đồ uống", R.drawable.drinks));
        list.add(new Topic("Flower", "Loài hoa", R.drawable.flowers));
        list.add(new Topic("Food", "Đồ ăn", R.drawable.food));
        list.add(new Topic("Country", "Quốc gia", R.drawable.country));
        list.add(new Topic("House", "Ngôi nhà", R.drawable.house));
        list.add(new Topic("Study", "Học tập", R.drawable.study));
        list.add(new Topic("Sport", "Thể thao", R.drawable.sports));
        list.add(new Topic("Vegetable", "Rau củ", R.drawable.vegetables));
        list.add(new Topic("Vehicle", "Phương tiện", R.drawable.vehicles));
        list.add(new Topic("Job", "Công việc", R.drawable.jobs));
        list.add(new Topic("Body", "Cơ thể", R.drawable.body));
        list.add(new Topic("Place", "Địa điểm", R.drawable.place));
        list.add(new Topic("Christmas", "Giáng sinh", R.drawable.christmas));

        topicList = list;
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