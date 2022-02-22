package com.example.hoctienganh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.Crown;
import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.my_interface.IClickUseCrown;

import java.util.List;

public class CrownCollectionAdapter extends RecyclerView.Adapter<CrownCollectionAdapter.CrownCollectionViewHolder> {

    private List<Crown> mList;
    private IClickUseCrown iClickUseCrown;
    private int currentCrown;

    public CrownCollectionAdapter(IClickUseCrown listener) {
        this.iClickUseCrown = listener;
    }

    public void setData(List<Crown> list, int position){
        this.mList = list;
        this.currentCrown = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CrownCollectionAdapter.CrownCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crown_collection, parent, false);
        return new CrownCollectionAdapter.CrownCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrownCollectionAdapter.CrownCollectionViewHolder holder, int position) {
        Crown crown = mList.get(position);
        if (crown == null) {
            return;
        }

        holder.tvCrownName.setText(crown.getName());
        holder.imgCrown.setImageResource(crown.getIdImage());
        if (position == currentCrown) {
            holder.tvUse.setText("Đang dùng");
            holder.tvUse.setBackgroundResource(R.drawable.button_use);
        }
        else {
            holder.tvUse.setBackgroundResource(R.drawable.button_use_2);
            holder.tvUse.setText("Sử dụng");
            holder.tvUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyData.soundEffect.start();
                    iClickUseCrown.onClickUse(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public class CrownCollectionViewHolder extends RecyclerView.ViewHolder {

        TextView tvCrownName;
        ImageView imgCrown;
        TextView tvUse;

        public CrownCollectionViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCrownName = itemView.findViewById(R.id.tv_crown_name_collection);
            imgCrown = itemView.findViewById(R.id.img_crown_collection);
            tvUse = itemView.findViewById(R.id.tv_use_collection);
        }
    }
}
