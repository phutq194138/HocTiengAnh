package com.example.hoctienganh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.hoctienganh.Character;
import com.example.hoctienganh.R;
import com.example.hoctienganh.adapter.CharacterAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SortCharacter extends AppCompatActivity {

    private RecyclerView rcvCharacter;
    private ImageView imgQuestion;
    private List<Character> listCharacter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_character);

        rcvCharacter = this.findViewById(R.id.rcv_character);
        imgQuestion = this.findViewById(R.id.img_question_sort);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        CharacterAdapter characterAdapter = new CharacterAdapter();

        createData();
        characterAdapter.setData(listCharacter);

        rcvCharacter.setLayoutManager(linearLayoutManager);
        rcvCharacter.setAdapter(characterAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int positionFrom = viewHolder.getAdapterPosition();
                int positionTo = target.getAdapterPosition();

                Collections.swap(listCharacter, positionFrom, positionTo);
                characterAdapter.notifyItemMoved(positionFrom, positionTo);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        itemTouchHelper.attachToRecyclerView(rcvCharacter);
    }

    private void createData(){

        listCharacter.add(new Character("a"));
        listCharacter.add(new Character("p"));
        listCharacter.add(new Character("p"));
        listCharacter.add(new Character("l"));
        listCharacter.add(new Character("e"));

        listCharacter.add(new Character("a"));
        listCharacter.add(new Character("p"));
        listCharacter.add(new Character("p"));

    }
}