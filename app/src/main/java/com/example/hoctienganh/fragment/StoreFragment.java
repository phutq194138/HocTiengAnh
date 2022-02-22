package com.example.hoctienganh.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoctienganh.Crown;
import com.example.hoctienganh.MainActivity;
import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.activity.ActivityLogin;
import com.example.hoctienganh.adapter.CrownStoreAdapter;
import com.example.hoctienganh.database.CrownDatabase;
import com.example.hoctienganh.database.UserDatabase;
import com.example.hoctienganh.my_interface.IClickBuyCrown;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {

    private View view;
    private RecyclerView rcvCrownStore;
    private List<Crown> crownList;
    private CrownStoreAdapter crownStoreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initUi();
        readData();
        crownStoreAdapter = new CrownStoreAdapter(crownList, new IClickBuyCrown() {
            @Override
            public void onClickBuyCrown(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận!");
                builder.setMessage("Bạn có muốn mua Vương miện " + crownList.get(position).getName());
                builder.setCancelable(false);

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MyData.user.getStar() < crownList.get(position).getPrice()){
                            Toast.makeText(getContext(), "Không đủ số sao.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            MyData.user.setStar(MyData.user.getStar() - crownList.get(position).getPrice());
                            UserDatabase.getInstance(getContext()).userDAO().updateUser(MyData.user);
                            Toast.makeText(getContext(), "Mua thành công.", Toast.LENGTH_SHORT).show();
                            CrownDatabase.getInstance(getContext()).crownDAO().insertCrown(new Crown(MyData.user.getUsername(), crownList.get(position).getName(), crownList.get(position).getIdResource(), crownList.get(position).getPrice()));
                            crownList.remove(position);
                            crownStoreAdapter.notifyDataSetChanged();
                        }

                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        rcvCrownStore.setAdapter(crownStoreAdapter);

        return view;
    }

    private void initUi(){
        rcvCrownStore = view.findViewById(R.id.rcv_store);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvCrownStore.setLayoutManager(gridLayoutManager);
    }

    private void readData(){
        List<Crown> list = new ArrayList<>();

        //list.add(new Crown(MyData.user.getUsername(), "Người tuyết", R.drawable.snowman_crown, 0));

        //list.add(new Crown(MyData.user.getUsername(), "Đồng", R.drawable.bronze_crown, 0));
        //list.add(new Crown(MyData.user.getUsername(), "Bạc", R.drawable.silver_crown, 0));
        //list.add(new Crown(MyData.user.getUsername(), "Vàng", R.drawable.gold_crown, 0));
        //list.add(new Crown(MyData.user.getUsername(), "Bạch kim", R.drawable.platinum_crown, 0));
        //list.add(new Crown(MyData.user.getUsername(), "Kim cương", R.drawable.diamond_crown, 0));
        //list.add(new Crown(MyData.user.getUsername(), "Kim cương đỏ", R.drawable.red_diamond_crown, 0));

        list.add(new Crown(MyData.user.getUsername(), "Bình minh", R.drawable.aurora_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Hoa hồng", R.drawable.flora_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Vòng hoa", R.drawable.flower_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Đá quý", R.drawable.gemini_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Huyền bí", R.drawable.mystic_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Neon", R.drawable.neon_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Lam ngọc", R.drawable.sapphire_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Mùa hè", R.drawable.summer_crown, 200));
        list.add(new Crown(MyData.user.getUsername(), "Lễ hội", R.drawable.vacation_crown, 200));

        List<Crown> userCrown = CrownDatabase.getInstance(getContext()).crownDAO().getListCrown(MyData.user.getUsername());

        for (int i = 0; i <userCrown.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getName().equals(userCrown.get(i).getName())) {
                    list.remove(j);
                    break;
                }
            }
        }

        crownList = list;
    }

    @Override
    public void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }
}
