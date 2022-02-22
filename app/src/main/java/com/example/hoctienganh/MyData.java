package com.example.hoctienganh;

import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class MyData {
    public static Data data;
    public static User user;
    public static MediaPlayer soundBackground;
    public static MediaPlayer soundEffect;
    public static MediaPlayer nhacnenCo;
    public static MediaPlayer hieuungCo;
    public static MediaPlayer nhacnenKhong;
    public static MediaPlayer hieuungKhong;
    public static String USERNAME = "usernameRemember";
    public static String PASSWORD = "passwordRemember";
    public static String IS_CHECKED = "isCheckedRemember" ;
    public static SharedPreferences sharedPreferences;
    public static String HAVE_SOUND = "have sound";
    public static String HAVE_EFFECT = "have effect";
}
