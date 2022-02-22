package com.example.hoctienganh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.R;
import com.example.hoctienganh.Topic;
import com.example.hoctienganh.custom.MyImageView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{

    private List<Topic> mListTopic;
    private IClickTopic iClickTopic;

    public TopicAdapter(IClickTopic listener) {
        this.iClickTopic = listener;
    }

    public void setData(List<Topic> list){
        this.mListTopic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = mListTopic.get(position);
        if (topic == null){
            return;
        }

        holder.imgTopic.setImageResource(topic.getIdRes());
        holder.tvTopicName.setText(topic.getName());
        holder.tvTopicMean.setText(topic.getMean());

        holder.layoutTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickTopic.onClickTopic(topic);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListTopic == null) {
            return 0;
        }
        return mListTopic.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {

        private MyImageView imgTopic;
        private TextView tvTopicName, tvTopicMean;
        private LinearLayout layoutTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTopic = itemView.findViewById(R.id.img_topic);
            tvTopicName = itemView.findViewById(R.id.tv_topic_name);
            tvTopicMean = itemView.findViewById(R.id.tv_topic_mean);
            layoutTopic = itemView.findViewById(R.id.layout_topic);

        }
    }

    public interface IClickTopic{
        public void onClickTopic(Topic topic);
    }
}
