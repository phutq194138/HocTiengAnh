package com.example.hoctienganh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.adapter.ViewPagerStoreAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityStore extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerStoreAdapter viewPagerStoreAdapter;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        imgBack = this.findViewById(R.id.img_back_store);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initUi();

        viewPagerStoreAdapter = new ViewPagerStoreAdapter(this);
        viewPager.setAdapter(viewPagerStoreAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Cửa hàng");
                }
                else if (position == 1){
                    tab.setText("Bộ sưu tập");
                }
            }
        }).attach();
    }

    private void initUi(){
        tabLayout = this.findViewById(R.id.tab_layout_store);
        viewPager = this.findViewById(R.id.viewpager_store);
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