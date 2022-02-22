package com.example.hoctienganh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.skydoves.progressview.ProgressView;

public class ActivityLevel extends AppCompatActivity {

    private TextView tvLevel, tvExp, tvNextLevel;
    private ProgressView progressView;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        imgBack = this.findViewById(R.id.img_back_level);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        initUi();
        setData();

    }

    private void initUi(){
        tvLevel = this.findViewById(R.id.tv_level_level);
        tvExp = this.findViewById(R.id.tv_exp_level);
        tvNextLevel = this.findViewById(R.id.tv_next_level);
        progressView = this.findViewById(R.id.progress_lever);
    }

    private void setData(){
        int level = MyData.user.getLevel();
        tvLevel.setText("" + level);
        if (level == 30) {
            tvNextLevel.setText("Tối đa");
            tvExp.setText("Tối đa");
            progressView.setProgress(100);
        }
        else {
            tvNextLevel.setText("Cấp " + (level+1));
            int expCurr = MyData.user.getExp() - MyData.user.getExpCurrentLevel();
            int expNext = MyData.user.getExpNextLevel() - MyData.user.getExpCurrentLevel();
            tvExp.setText("" + expCurr + "/" + expNext);
            float progress = expCurr*100/expNext;
            progressView.setProgress(progress);
        }
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