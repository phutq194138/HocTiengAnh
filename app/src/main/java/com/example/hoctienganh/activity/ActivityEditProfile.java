package com.example.hoctienganh.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.database.UserDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityEditProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private CircleImageView imgAvatar;
    private EditText edtName, edtPassword;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initUi();
        setData();
    }

    private void initUi() {
        imgBack = this.findViewById(R.id.img_back_edit_profile);
        imgAvatar = this.findViewById(R.id.img_avatar_profile);
        edtName = this.findViewById(R.id.edt_name_profile);
        edtPassword = this.findViewById(R.id.edt_new_password_profile);
        btnComplete = this.findViewById(R.id.btn_complete_profile);

        imgAvatar.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    private void setData(){
        edtName.setText(MyData.user.getFullName());
        edtPassword.setText(MyData.user.getPassword());

        Bitmap image = null;
        image = BitmapFactory.decodeByteArray(MyData.user.getImage(), 0, MyData.user.getImage().length);
        imgAvatar.setImageBitmap(image);
    }

    private void actionComplete(){
        String newName = "";
        String newPassword = "";

        newName = edtName.getText().toString();
        newPassword = edtPassword.getText().toString();

        MyData.user.setFullName(newName);
        MyData.user.setPassword(newPassword);

        BitmapDrawable drawable =(BitmapDrawable) imgAvatar.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        MyData.user.setImage(byteArray);

        UserDatabase.getInstance(this).userDAO().updateUser(MyData.user);
        finish();
    }

    private void actionChangeAvatar(){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgAvatar.setImageBitmap(image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.img_back_edit_profile:
                finish();
                break;

            case R.id.btn_complete_profile:
                actionComplete();
                break;

            case R.id.img_avatar_profile:
                actionChangeAvatar();
                break;

            default:
                break;

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
}