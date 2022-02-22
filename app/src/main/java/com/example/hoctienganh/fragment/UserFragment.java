package com.example.hoctienganh.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.User;
import com.example.hoctienganh.activity.ActivityEditProfile;
import com.example.hoctienganh.activity.ActivityFavorite;
import com.example.hoctienganh.activity.ActivityHistory;
import com.example.hoctienganh.activity.ActivityLevel;
import com.example.hoctienganh.activity.ActivityLogin;
import com.example.hoctienganh.activity.ActivitySetting;
import com.example.hoctienganh.activity.ActivityStore;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private Button btnLogin, btnLogout, btnNotification, btnStore, btnLevel, btnFavorite, btnSetting, btnEditProfile, btnStar;
    private TextView tvUserFullName;
    private CircleImageView imgUser;
    private ImageView imgCrown;
    private ScrollView linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MyData.soundBackground.start();

        initUi();
        setLayoutFragment();
        return mView;
    }

    private void initUi(){
        btnLogin = mView.findViewById(R.id.btn_login);
        btnLogout = mView.findViewById(R.id.btn_logout);
        btnNotification = mView.findViewById(R.id.btn_notification);
        btnStore = mView.findViewById(R.id.btn_store);
        btnLevel = mView.findViewById(R.id.btn_level);
        btnFavorite = mView.findViewById(R.id.btn_favorite);
        btnSetting = mView.findViewById(R.id.btn_setting);
        btnEditProfile = mView.findViewById(R.id.btn_edit_profile);
        tvUserFullName = mView.findViewById(R.id.tv_user_full_name);
        imgUser = mView.findViewById(R.id.img_avatar_user);
        imgCrown = mView.findViewById(R.id.img_crown_user);
        btnStar = mView.findViewById(R.id.btn_user_point);
        linearLayout = mView.findViewById(R.id.layout_user);

        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btnStore.setOnClickListener(this);
        btnLevel.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnStar.setOnClickListener(this);
    }

    private void setLayoutFragment(){
        if (MyData.user == null){
            btnLogin.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        }

        else{
            btnLogin.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        setDataUser(MyData.user);
    }

    private void setDataUser(User user){

        if (user == null) {
            return;
        }
        btnStar.setText(String.valueOf(user.getStar()));
        imgCrown.setImageResource(user.getCrown().getIdImage());
        tvUserFullName.setText(user.getFullName());
        Bitmap image = null;
        image = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
        imgUser.setImageBitmap(image);

    }

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.btn_login:
                actionLogin();
                break;
            case R.id.btn_edit_profile:
                actionEditProfile();
                break;
            case R.id.btn_notification:
                actionHistory();
                break;
            case R.id.btn_store:
                actionStore();
                break;
            case R.id.btn_level:
                actionLevel();
                break;
            case R.id.btn_favorite:
                actionFavorite();
                break;
            case R.id.btn_setting:
                actionSetting();
                break;
            case R.id.btn_logout:
                actionLogout();
                break;
            default:
                break;
        }
    }

    private void actionLogout() {
        MyData.user = null;
        SharedPreferences.Editor editor = MyData.sharedPreferences.edit();
        editor.remove(MyData.USERNAME);
        editor.remove(MyData.PASSWORD);
        editor.remove(MyData.IS_CHECKED);
        editor.commit();
        onResume();
    }

    private void actionSetting() {
        Intent intentSetting = new Intent(getContext(), ActivitySetting.class);
        startActivity(intentSetting);
    }

    private void actionFavorite() {
        Intent intentFavorite = new Intent(getContext(), ActivityFavorite.class);
        startActivity(intentFavorite);
    }

    private void actionLevel() {
        Intent intentLevel = new Intent(getContext(), ActivityLevel.class);
        startActivity(intentLevel);
    }

    private void actionStore() {
        Intent intentStore = new Intent(getContext(), ActivityStore.class);
        startActivity(intentStore);
    }

    private void actionHistory() {
        Intent intentHistory = new Intent(getContext(), ActivityHistory.class);
        startActivity(intentHistory);
    }

    private void actionEditProfile() {
        Intent intentEditProfile = new Intent(getContext(), ActivityEditProfile.class);
        startActivity(intentEditProfile);
    }

    private void actionLogin() {
        Intent intentLogin = new Intent(getContext(), ActivityLogin.class);
        startActivity(intentLogin);
    }

    @Override
    public void onResume() {
        setLayoutFragment();
        MyData.soundBackground.start();
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
}
