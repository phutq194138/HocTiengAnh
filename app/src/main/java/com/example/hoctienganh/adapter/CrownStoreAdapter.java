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
import com.example.hoctienganh.my_interface.IClickBuyCrown;

import java.util.List;

public class CrownStoreAdapter extends RecyclerView.Adapter<CrownStoreAdapter.CrownStoreViewHolder>{

    private List<Crown> mList;
    private IClickBuyCrown iClickBuyCrown;

    public CrownStoreAdapter(List<Crown> list, IClickBuyCrown listener) {
        this.mList = list;
        this.iClickBuyCrown = listener;
    }

    @NonNull
    @Override
    public CrownStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crown_store, parent, false);
        return new CrownStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrownStoreViewHolder holder, int position) {
        Crown crown = mList.get(position);
        if (crown == null) {
            return;
        }

        holder.tvCrownName.setText(crown.getName());
        holder.imgCrown.setImageResource(crown.getIdImage());
        holder.btnBuy.setText(""+crown.getPrice());
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                iClickBuyCrown.onClickBuyCrown(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public class CrownStoreViewHolder extends RecyclerView.ViewHolder {

        TextView tvCrownName;
        ImageView imgCrown;
        Button btnBuy;

        public CrownStoreViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCrownName = itemView.findViewById(R.id.tv_crown_name_store);
            imgCrown = itemView.findViewById(R.id.img_crown_store);
            btnBuy = itemView.findViewById(R.id.btn_buy_store);
        }
    }
}
