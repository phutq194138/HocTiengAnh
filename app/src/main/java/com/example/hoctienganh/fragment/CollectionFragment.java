package com.example.hoctienganh.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.Crown;
import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.adapter.CrownCollectionAdapter;
import com.example.hoctienganh.database.CrownDatabase;
import com.example.hoctienganh.database.UserDatabase;
import com.example.hoctienganh.my_interface.IClickUseCrown;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment {

    private View view;
    private RecyclerView rcvCrownCollection;
    private List<Crown> crownList;
    private int currentCrown;
    private CrownCollectionAdapter crownCollectionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        readData();
        initUi();

        crownCollectionAdapter.setData(crownList, currentCrown);
        rcvCrownCollection.setAdapter(crownCollectionAdapter);

        return view;
    }

    private void initUi(){
        rcvCrownCollection = view.findViewById(R.id.rcv_collection);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvCrownCollection.setLayoutManager(gridLayoutManager);

        crownCollectionAdapter = new CrownCollectionAdapter(new IClickUseCrown() {
            @Override
            public void onClickUse(int position) {
                currentCrown = position;
                MyData.user.setCrownName(crownList.get(position).getName());
                UserDatabase.getInstance(getContext()).userDAO().updateUser(MyData.user);
                onResume();
            }
        });
    }

    private void readData(){
        List<Crown> list = CrownDatabase.getInstance(getContext()).crownDAO().getListCrown(MyData.user.getUsername());
        int level = MyData.user.getLevel();
        list.add(new Crown(MyData.user.getUsername(), "Người tuyết", R.drawable.snowman_crown, 0));

        if (level >=5) list.add(new Crown(MyData.user.getUsername(), "Đồng", R.drawable.bronze_crown, 0));
        if (level >=10) list.add(new Crown(MyData.user.getUsername(), "Bạc", R.drawable.silver_crown, 0));
        if (level >=15) list.add(new Crown(MyData.user.getUsername(), "Vàng", R.drawable.gold_crown, 0));
        if (level >=20) list.add(new Crown(MyData.user.getUsername(), "Bạch kim", R.drawable.platinum_crown, 0));
        if (level >=25) list.add(new Crown(MyData.user.getUsername(), "Kim cương", R.drawable.diamond_crown, 0));
        if (level >=30) list.add(new Crown(MyData.user.getUsername(), "Kim cương đỏ", R.drawable.red_diamond_crown, 0));

        crownList = list;
        for (int i = 0; i < crownList.size(); i++){
            if (MyData.user.getCrownName().equals(crownList.get(i).getName())){
                currentCrown = i;
                break;
            }
        }
    }

    @Override
    public void onResume() {
        MyData.soundBackground.start();
        readData();
        crownCollectionAdapter.setData(crownList, currentCrown);
        super.onResume();
    }

    @Override
    public void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    public void onStart() {
        MyData.soundBackground.start();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }

}
