package com.example.hoctienganh.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Word;
import com.example.hoctienganh.custom.MyImageView;

import java.util.List;
import java.util.Locale;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{

    private TextToSpeech textToSpeech;
    private List<Word> mList;

    public WordAdapter(TextToSpeech textToSpeech){
        this.textToSpeech = textToSpeech;
    }

    public void setData(List<Word> list){
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = mList.get(position);

        if (word == null){
            return;
        }

        holder.imgWord.setImageResource(word.getIdRes());
        holder.tvWord.setText(word.getWord());
        holder.tvWordMean.setText(word.getMean());
        holder.layoutWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        private MyImageView imgWord;
        private TextView tvWord, tvWordMean;
        private LinearLayout layoutWord;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            imgWord = itemView.findViewById(R.id.img_word);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvWordMean = itemView.findViewById(R.id.tv_word_mean);
            layoutWord = itemView.findViewById(R.id.layout_word);
        }
    }
}
