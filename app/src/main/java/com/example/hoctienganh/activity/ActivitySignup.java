package com.example.hoctienganh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hoctienganh.Crown;
import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.User;
import com.example.hoctienganh.database.CrownDatabase;
import com.example.hoctienganh.database.UserDatabase;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ActivitySignup extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtFullName;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUi();
    }

    private void initUi(){
        edtUsername = this.findViewById(R.id.edt_username_signup);
        edtPassword = this.findViewById(R.id.edt_password_signup);
        edtFullName = this.findViewById(R.id.edt_fullname_signup);
        btnSignup = this.findViewById(R.id.btn_signup_signup);

        edtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
            }
        });

        edtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
            }
        });

        edtFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                actionSignUp();
            }
        });
    }

    private void actionSignUp(){
        String username = "";
        String password = "";
        String fullname = "";

        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        fullname = edtFullName.getText().toString();

        if (check(username)){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_default);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            User user = new User(username, password, fullname, byteArray);
            UserDatabase.getInstance(this).userDAO().insertUser(user);
            signinSucces();
        }

        else {
            signinFalse();
        }
    }

    private boolean check(String username) {
        List<User> list = UserDatabase.getInstance(this).userDAO().getListUser(username);
        if (list.size() == 0) return true;
        return false;
    }

    private void signinFalse() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Thông báo!");
        alertDialogBuilder.setMessage("Tên đăng nhập đã tồn tại.\n Vui lòng thử lại.");

        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyData.soundEffect.start();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void signinSucces() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Thông báo!");
        alertDialogBuilder.setMessage("Đăng ký thành công.");

        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyData.soundEffect.start();
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
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