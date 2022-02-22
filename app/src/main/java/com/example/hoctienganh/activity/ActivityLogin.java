package com.example.hoctienganh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.R;
import com.example.hoctienganh.User;
import com.example.hoctienganh.database.UserDatabase;

import java.util.List;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnShowPassword;
    private TextView tvForgetPassword, tvSignUp;
    private CheckBox cbRemember;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();

        edtUsername.setText(MyData.sharedPreferences.getString(MyData.USERNAME, ""));
        edtPassword.setText(MyData.sharedPreferences.getString(MyData.PASSWORD, ""));
        cbRemember.setChecked(MyData.sharedPreferences.getBoolean(MyData.IS_CHECKED, false));
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initUi() {
        edtUsername = this.findViewById(R.id.edt_username_login);
        edtPassword = this.findViewById(R.id.edt_password_login);
        btnLogin = this.findViewById(R.id.btn_login_login);
        tvForgetPassword = this.findViewById(R.id.tv_forget_password);
        tvSignUp = this.findViewById(R.id.tv_signup_login);
        cbRemember = this.findViewById(R.id.cb_remember);
        btnShowPassword = this.findViewById(R.id.btn_show_password);

        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow == false){
                    isShow = true;
                    edtPassword.setTransformationMethod(null);
                    btnShowPassword.setBackgroundResource(R.drawable.ic_eye);
                }
                else {
                    isShow = false;
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    btnShowPassword.setBackgroundResource(R.drawable.ic_eye_off);
                }
            }
        });

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        edtPassword.setOnClickListener(this);
        edtUsername.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.btn_login_login:
                actionLogin();
                break;
            case R.id.tv_forget_password:
                actionForgetPassword();
                break;
            case R.id.tv_signup_login:
                actionSignup();
                break;
            default:
                break;
        }
    }

    private void actionLogin(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (check(username, password)){
            actionLoginSuccess();
            if (cbRemember.isChecked()){
                SharedPreferences.Editor editor = MyData.sharedPreferences.edit();
                editor.putString(MyData.USERNAME, username);
                editor.putString(MyData.PASSWORD, password);
                editor.putBoolean(MyData.IS_CHECKED, true);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = MyData.sharedPreferences.edit();
                editor.remove(MyData.USERNAME);
                editor.remove(MyData.PASSWORD);
                editor.remove(MyData.IS_CHECKED);
                editor.commit();
            }
        }
        else{
            actionLoginFalse();
        }
    }

    private boolean check(String username, String password) {
        List<User> list = UserDatabase.getInstance(this).userDAO().getListUser(username);
        if (list.size() != 0){
            User user = list.get(0);
            if (user.getPassword().equals(password)){
                MyData.user = user;
                return true;
            }
        }
        return false;
    }

    private void actionLoginFalse() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Thông báo!");
        alertDialogBuilder.setMessage("Tên đăng nhập hoặc mật khẩu không đúng.\n Vui lòng thử lại.");

        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void actionLoginSuccess() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Thông báo!");
        alertDialogBuilder.setMessage("Đăng nhập thành công.");

        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void actionForgetPassword(){
        Intent intentForgetPassword = new Intent(this, ActivityForgetPassword.class);
        startActivity(intentForgetPassword);
    }

    private void actionSignup(){
        Intent intentSignup = new Intent(this, ActivitySignup.class);
        startActivity(intentSignup);
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