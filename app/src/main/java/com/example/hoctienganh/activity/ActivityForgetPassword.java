package com.example.hoctienganh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.User;
import com.example.hoctienganh.database.UserDatabase;

import java.util.List;

public class ActivityForgetPassword extends AppCompatActivity {

    private EditText edtUsername;
    private Button btnGetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ImageView imgBack = this.findViewById(R.id.img_back_forget_password);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                finish();
            }
        });

        edtUsername = this.findViewById(R.id.edt_username_forget_password);
        btnGetPassword = this.findViewById(R.id.btn_get_password);

        btnGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPassword();
            }
        });


    }

    private void getPassword(){
        String username = "";
        username = edtUsername.getText().toString().trim();

        if (username.equals("")){
            Toast.makeText(this, "Nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
        }
        else {
            List<User> list = UserDatabase.getInstance(this).userDAO().getListUser(username);
            if (list.size()==0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Thông báo!");
                alertDialogBuilder.setMessage("Tên đăng nhập không tồn tại");

                alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
            else {
                User user = list.get(0);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Thông báo!");
                String str = "Tài khoản của bạn:\nTên đăng nhập: " + user.getUsername() +"\n" + "Mật khẩu:" + user.getPassword();
                alertDialogBuilder.setMessage(str);

                alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        }
    }
}