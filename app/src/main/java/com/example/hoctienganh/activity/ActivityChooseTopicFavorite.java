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

public class ActivityChooseTopicFavorite extends AppCompatActivity {

    private RecyclerView rcvTopic;
    private List<Topic> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic_favorite);

        ImageView imgBack = this.findViewById(R.id.img_back_choose_topic_favorite);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        rcvTopic = this.findViewById(R.id.rcv_topic_favorite);
        createDataTopic();
        TopicAdapter topicAdapter = new TopicAdapter(new TopicAdapter.IClickTopic() {
            @Override
            public void onClickTopic(Topic topic) {
                MyData.soundEffect.start();
                actionClickTopic(topic);
            }
        });
        topicAdapter.setData(topicList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvTopic.setLayoutManager(gridLayoutManager);

        rcvTopic.setAdapter(topicAdapter);
    }

    private void actionClickTopic(Topic topic){
        Intent intentVocabulary = new Intent(this, ActivityChooseWord.class);
        intentVocabulary.putExtra("topic name", topic.getName());
        startActivity(intentVocabulary);
    }

    private void createDataTopic(){
        List<Topic> list = new ArrayList<>();

        list.add(new Topic("Alphabet", "Ch??? c??i", R.drawable.alphabet));
        list.add(new Topic("Number", "Ch??? s???", R.drawable.number));
        list.add(new Topic("Animal", "?????ng v???t", R.drawable.animal));
        list.add(new Topic("Color", "M??u s???c", R.drawable.color));
        list.add(new Topic("Clothes", "Th???i trang", R.drawable.clothes));
        list.add(new Topic("Fruit","Tr??i c??y", R.drawable.fruits));
        list.add(new Topic("Drink", "????? u???ng", R.drawable.drinks));
        list.add(new Topic("Flower", "Lo??i hoa", R.drawable.flowers));
        list.add(new Topic("Food", "????? ??n", R.drawable.food));
        list.add(new Topic("Country", "Qu???c gia", R.drawable.country));
        list.add(new Topic("House", "Ng??i nh??", R.drawable.house));
        list.add(new Topic("Study", "H???c t???p", R.drawable.study));
        list.add(new Topic("Sport", "Th??? thao", R.drawable.sports));
        list.add(new Topic("Vegetable", "Rau c???", R.drawable.vegetables));
        list.add(new Topic("Vehicle", "Ph????ng ti???n", R.drawable.vehicles));
        list.add(new Topic("Job", "C??ng vi???c", R.drawable.jobs));
        list.add(new Topic("Body", "C?? th???", R.drawable.body));
        list.add(new Topic("Place", "?????a ??i???m", R.drawable.place));
        list.add(new Topic("Christmas", "Gi??ng sinh", R.drawable.christmas));

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