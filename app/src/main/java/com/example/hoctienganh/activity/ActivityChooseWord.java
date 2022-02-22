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
import com.example.hoctienganh.WordFavorite;
import com.example.hoctienganh.adapter.WordFavoriteAdapter;
import com.example.hoctienganh.database.WordFavoriteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityChooseWord extends AppCompatActivity {

    private RecyclerView rcvWord;
    private WordFavoriteAdapter wordFavoriteAdapter;
    private TextView tvTopicName;
    private List<WordFavorite> listWordFavorites;
    private List<Word> listWord;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_word);

        ImageView imgBack = this.findViewById(R.id.img_back_choose_word);

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



        tvTopicName = this.findViewById(R.id.tv_topic_name_choose);
        String topicName = getIntent().getStringExtra("topic name");
        tvTopicName.setText(topicName);

        listWord = findListWord(topicName);
        readData();

        rcvWord = findViewById(R.id.rcv_vocabulary_choose_word);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvWord.setLayoutManager(gridLayoutManager);

        wordFavoriteAdapter = new WordFavoriteAdapter(textToSpeech, new WordFavoriteAdapter.IClickFavorite() {
            @Override
            public void onClickFavorite(WordFavorite wordFavorite, int position) {
                MyData.soundEffect.start();
                actionClickWord(wordFavorite, position);
            }
        });

        wordFavoriteAdapter.setData(listWordFavorites);
        rcvWord.setAdapter(wordFavoriteAdapter);
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

    private void readData(){
        List<WordFavorite> list2 = new ArrayList<>();
        List<WordFavorite> list = WordFavoriteDatabase.getInstance(this).wordFavoriteDAO().getListWord(MyData.user.getUsername());
        for (Word word:listWord){
            list2.add(new WordFavorite(MyData.user.getUsername(), word.getWord(), word.getMean(), word.getIdRes(), false));
        }
        if (list.size()!=0){
            for (WordFavorite wordFavorite:list2){
                for (WordFavorite wordFavorite1:list){
                    if (wordFavorite1.getWord().equals(wordFavorite.getWord())&&wordFavorite1.getMean().equals(wordFavorite1.getMean())){
                        wordFavorite.setLike(true);
                        break;
                    }
                }
            }
        }
        listWordFavorites = list2;
    }

    private void actionClickWord(WordFavorite wordFavorite, int position){
        if (wordFavorite.isLike()==false){
            listWordFavorites.get(position).setLike(true);
            WordFavoriteDatabase.getInstance(this).wordFavoriteDAO().insertWord(wordFavorite);
            wordFavoriteAdapter.notifyItemChanged(position);
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