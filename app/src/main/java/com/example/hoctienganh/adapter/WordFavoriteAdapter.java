package com.example.hoctienganh.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.R;
import com.example.hoctienganh.WordFavorite;
import com.example.hoctienganh.custom.MyImageView;

import java.util.List;

public class WordFavoriteAdapter extends RecyclerView.Adapter<WordFavoriteAdapter.WordFavoriteViewHolder>{

    private List<WordFavorite> listWord;
    private TextToSpeech textToSpeech;
    private IClickFavorite iClickFavorite;


    public WordFavoriteAdapter(TextToSpeech textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public WordFavoriteAdapter(TextToSpeech textToSpeech, IClickFavorite listener) {
        this.textToSpeech = textToSpeech;
        this.iClickFavorite = listener;
    }

    public void setData(List<WordFavorite> listWord){
        this.listWord = listWord;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_favorite, parent, false);
        return new WordFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordFavoriteViewHolder holder, int position) {
        WordFavorite word = listWord.get(position);
        if (word == null) {
            return;
        }

        holder.imgWord.setImageResource(word.getIdResource());
        holder.tvWord.setText(word.getWord());
        holder.tvMean.setText(word.getMean());

        if (word.isLike()==true){
            holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
        else {
            holder.btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }

        holder.layoutWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickFavorite.onClickFavorite(word, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listWord == null) {
            return 0;
        }
        return listWord.size();
    }

    public class WordFavoriteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutWord;
        MyImageView imgWord;
        TextView tvWord, tvMean;
        Button btnFavorite;

        public WordFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutWord = itemView.findViewById(R.id.layout_word_favorite);
            imgWord = itemView.findViewById(R.id.img_word_favorite);
            tvWord = itemView.findViewById(R.id.tv_word_favorite);
            tvMean = itemView.findViewById(R.id.tv_word_mean_favorite);
            btnFavorite = itemView.findViewById(R.id.btn_is_favorite);
        }
    }

    public interface IClickFavorite{
        public void onClickFavorite(WordFavorite wordFavorite, int position);
    }
}
