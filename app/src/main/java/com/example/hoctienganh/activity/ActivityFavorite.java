package com.example.hoctienganh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hoctienganh.MainActivity;
import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.WordFavorite;
import com.example.hoctienganh.adapter.WordFavoriteAdapter;
import com.example.hoctienganh.database.WordFavoriteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityFavorite extends AppCompatActivity {

    private RecyclerView rcvWord;
    private WordFavoriteAdapter wordFavoriteAdapter;
    private List<WordFavorite> listWord;
    private TextToSpeech textToSpeech;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ImageView imgBack = this.findViewById(R.id.img_back_favorite);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        readData();

        btnAdd = this.findViewById(R.id.btn_adđ_favorite);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionAdd();
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

        rcvWord = this.findViewById(R.id.rcv_word_favorite);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvWord.setLayoutManager(gridLayoutManager);

        wordFavoriteAdapter = new WordFavoriteAdapter(textToSpeech, new WordFavoriteAdapter.IClickFavorite() {
            @Override
            public void onClickFavorite(WordFavorite wordFavorite, int position) {
                MyData.soundEffect.start();
                actionClickWord(wordFavorite, position);
            }
        });
        wordFavoriteAdapter.setData(listWord);
        rcvWord.setAdapter(wordFavoriteAdapter);

    }

    private void readData(){
        List<WordFavorite> list = WordFavoriteDatabase.getInstance(this).wordFavoriteDAO().getListWord(MyData.user.getUsername());

        listWord = list;
    }

    private void actionAdd(){
        Intent intentAdd = new Intent(this, ActivityChooseTopicFavorite.class);
        startActivity(intentAdd);
    }

    private void actionClickWord(WordFavorite wordFavorite, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có chắc chắn xóa?");
        builder.setCancelable(false);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyData.soundEffect.start();
                actionDelete(wordFavorite, position);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyData.soundEffect.start();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actionDelete(WordFavorite wordFavorite, int position){
        listWord.remove(position);
        wordFavoriteAdapter.setData(listWord);
        WordFavoriteDatabase.getInstance(this).wordFavoriteDAO().deleteWord(wordFavorite);
        Toast.makeText(this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        MyData.soundBackground.start();
        readData();
        wordFavoriteAdapter.setData(listWord);
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