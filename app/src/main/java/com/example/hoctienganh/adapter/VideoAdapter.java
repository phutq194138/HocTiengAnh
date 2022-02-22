package com.example.hoctienganh.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.hoctienganh.VideoYoutube;
import com.example.hoctienganh.activity.PlayVideo;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private IClickVideo iClickVideo;
    private List<VideoYoutube> listVideo;

    public VideoAdapter(IClickVideo iClickVideo) {
        this.iClickVideo = iClickVideo;
    }

    public void setData(List<VideoYoutube> list){
        this.listVideo = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoYoutube videoYoutube = listVideo.get(position);
        if (videoYoutube == null){
            return;
        }

        holder.imgVideoThumbnail.setImageResource(videoYoutube.getIdThumbnail());
        holder.tvVideoTitle.setText(videoYoutube.getTitle());
        holder.layoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickVideo.onClickVideo(videoYoutube);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listVideo == null) {
            return 0;
        }
        return listVideo.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoThumbnail;
        private TextView tvVideoTitle;
        private LinearLayout layoutVideo;


        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVideoThumbnail = itemView.findViewById(R.id.img_video_thumbnail);
            tvVideoTitle = itemView.findViewById(R.id.tv_video_title);
            layoutVideo = itemView.findViewById(R.id.layout_video);
        }
    }

    public interface IClickVideo{
        public void onClickVideo(VideoYoutube videoYoutube);
    }
}
