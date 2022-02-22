package com.example.hoctienganh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.Question;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Word;

import java.util.List;

public class WatchAndChooseResultAdapter extends RecyclerView.Adapter<WatchAndChooseResultAdapter.WatchAndChooseResultViewHolder> {


    private List<Question> mQuestionList;
    private List<Boolean> mBoolQuestion;
    private List<Word> mChosenList;

    public WatchAndChooseResultAdapter() {
    }

    public void setData(List<Question> mQuestionList,List<Boolean> mBoolQuestion, List<Word> mChosenList ){
        this.mQuestionList = mQuestionList;
        this.mBoolQuestion = mBoolQuestion;
        this.mChosenList = mChosenList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WatchAndChooseResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_and_choose_result, parent, false);
        return new WatchAndChooseResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchAndChooseResultViewHolder holder, int position) {
        Question question = mQuestionList.get(position);
        if (question == null) return;

        holder.imgRsWord.setBackgroundResource(question.getQuestion().getIdRes());
        holder.tvRsWord.setText(question.getQuestion().getWord());
        holder.tvRsMean.setText(question.getQuestion().getMean());
        holder.tvYourChoose.setText(mChosenList.get(position).getWord());
        if (mBoolQuestion.get(position) == false) holder.imgCorrect.setBackgroundResource(R.drawable.ic_wrong);
        else holder.imgCorrect.setBackgroundResource(R.drawable.ic_correct);
    }

    @Override
    public int getItemCount() {
        if (mQuestionList != null) {
            return mQuestionList.size();
        }
        return 0;
    }

    public class WatchAndChooseResultViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgRsWord, imgCorrect;
        private TextView tvRsWord, tvRsMean, tvYourChoose;

        public WatchAndChooseResultViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRsWord = itemView.findViewById(R.id.img_result);
            imgCorrect = itemView.findViewById(R.id.img_correct);

            tvRsWord = itemView.findViewById(R.id.tv_result_word);
            tvRsMean = itemView.findViewById(R.id.tv_result_mean);
            tvYourChoose = itemView.findViewById(R.id.tv_your_chosen);
        }
    }
}
