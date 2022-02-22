package com.example.hoctienganh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.R;
import com.example.hoctienganh.Test;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private List<Test> mListTest;

    public TestAdapter() {
    }

    public void setData(List<Test> list){
        this.mListTest = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Test test = mListTest.get(position);
        if (test == null) {
            return;
        }

        holder.tvNumber.setText("" + (position+1) +".");
        holder.tvHourStart.setText(test.getHourStart());
        holder.tvDayStart.setText(test.getDayStart());
        holder.tvHourEnd.setText(test.getHourEnd());
        holder.tvDayEnd.setText(test.getDayEnd());
        holder.tvPoint.setText("" + test.getPoint());
        holder.tvStar.setText("" + test.getStar());
        holder.tvExp.setText("" + test.getExp());
        holder.tvTime.setText("" + test.getTime() + "s");

    }

    @Override
    public int getItemCount() {
        if (mListTest == null) {
            return 0;
        }

        return mListTest.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {

        TextView tvHourStart, tvDayStart, tvHourEnd, tvDayEnd, tvTime, tvPoint, tvStar, tvExp, tvNumber;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHourStart = itemView.findViewById(R.id.tv_test_hour_start);
            tvDayStart = itemView.findViewById(R.id.tv_test_day_start);
            tvHourEnd = itemView.findViewById(R.id.tv_test_hour_end);
            tvDayEnd = itemView.findViewById(R.id.tv_test_day_end);
            tvTime = itemView.findViewById(R.id.tv_test_time);
            tvPoint = itemView.findViewById(R.id.tv_test_point);
            tvStar = itemView.findViewById(R.id.tv_test_star);
            tvExp = itemView.findViewById(R.id.tv_test_exp);
            tvNumber = itemView.findViewById(R.id.tv_stt);
        }
    }
}
