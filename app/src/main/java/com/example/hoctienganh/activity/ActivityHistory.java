package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Test;
import com.example.hoctienganh.adapter.TestAdapter;
import com.example.hoctienganh.database.TestDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistory extends AppCompatActivity {

    private ImageView imgBack;
    private RecyclerView rcvHistory;
    private List<Test> listTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        imgBack = this.findViewById(R.id.img_back_history);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        readData();

        rcvHistory = this.findViewById(R.id.rcv_history);
        TestAdapter testAdapter = new TestAdapter();
        testAdapter.setData(listTest);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        rcvHistory.setAdapter(testAdapter);
        rcvHistory.setLayoutManager(linearLayoutManager);
    }

    private void readData(){
        List<Test> list = TestDatabase.getInstance(this).testDAO().getListTest(MyData.user.getUsername());
        listTest = list;
    }

    @Override
    protected void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }
}