package com.example.hoctienganh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.Character;
import com.example.hoctienganh.R;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

    private List<Character> listCharacter;

    public void setData(List<Character> list){
        this.listCharacter = list;
        notifyDataSetChanged();
    }

    public CharacterAdapter() {
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = listCharacter.get(position);
        if (character == null) {
            return;
        }

        holder.tvCharacter.setText(character.getKey());
    }

    @Override
    public int getItemCount() {
        if (listCharacter == null){
            return 0;
        }
        return listCharacter.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCharacter;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCharacter = itemView.findViewById(R.id.tv_character);
        }
    }
}
