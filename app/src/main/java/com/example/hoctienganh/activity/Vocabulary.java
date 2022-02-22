package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Word;
import com.example.hoctienganh.adapter.WordAdapter;

import java.util.List;
import java.util.Locale;

public class Vocabulary extends AppCompatActivity {

    private RecyclerView rcvVocabulary;
    private WordAdapter wordAdapter;
    private String topicName;
    private TextView tvTopicName;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        ImageView imgBack = this.findViewById(R.id.img_back_vocabulary);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        topicName = getIntent().getStringExtra("topic name");
        rcvVocabulary = this.findViewById(R.id.rcv_vocabulary);
        tvTopicName = this.findViewById(R.id.tv_topic_name_vocabulary);
        tvTopicName.setText(topicName);
        wordAdapter = new WordAdapter(textToSpeech);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvVocabulary.setLayoutManager(gridLayoutManager);

        wordAdapter.setData(findListWord(topicName));
        rcvVocabulary.setAdapter(wordAdapter);

    }

    private List<Word> findListWord(String topicName){
        if (topicName.equals("Alphabet")) return MyData.data.getListAlphabet();
        if (topicName.equals("Animal")) return MyData.data.getListAnimal();
        if (topicName.equals("Color")) return MyData.data.getListColor();
        if (topicName.equals("Clothes")) return MyData.data.getListClothes();
        if (topicName.equals("Fruit")) return MyData.data.getListFruit();
        if (topicName.equals("Drink")) return MyData.data.getListDrink();
        if (topicName.equals("Flower")) return MyData.data.getListFlower();
        if (topicName.equals("Food")) return MyData.data.getListFood();
        if (topicName.equals("Country")) return MyData.data.getListCountry();
        if (topicName.equals("House")) return MyData.data.getListHouse();
        if (topicName.equals("Study")) return MyData.data.getListStudy();
        if (topicName.equals("Sport")) return MyData.data.getListSport();
        if (topicName.equals("Vegetable")) return MyData.data.getListVegetable();
        if (topicName.equals("Vehicle")) return MyData.data.getListVehicle();
        if (topicName.equals("Job")) return MyData.data.getListJob();
        if (topicName.equals("Body")) return MyData.data.getListBody();
        if (topicName.equals("Number")) return MyData.data.getListNumber();
        if (topicName.equals("Place")) return MyData.data.getListPlace();
        if (topicName.equals("Christmas")) return MyData.data.getListChristmas();

        return null;
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