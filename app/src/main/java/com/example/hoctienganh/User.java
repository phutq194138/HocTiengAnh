package com.example.hoctienganh;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.hoctienganh.database.CrownDatabase;

import java.util.List;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private String fullName;
    private int exp;
    private int point;
    private int star;
    private String crownName;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public User(String username, String password, String fullName, byte[] image) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.image = image;
        this.point = 0;
        this.exp = 0;
        this.star = 0;
        this.crownName = "Người tuyết";
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getLevel() {
        if (exp <= 100) return 1;
        else if (exp <= 300) return 2;
        else if (exp <= 800) return 3;
        else if (exp <= 1500) return 4;
        else if (exp <= 2300) return 5;
        else if (exp <= 3000) return 6;
        else if (exp <= 3800) return 7;
        else if (exp <= 4500) return 8;
        else if (exp <= 5300) return 9;
        else if (exp <= 6000) return 10;
        else if (exp <= 7000) return 11;
        else if (exp <= 8000) return 12;
        else if (exp <= 9000) return 13;
        else if (exp <= 10000) return 14;
        else if (exp <= 11500) return 15;
        else if (exp <= 13000) return 16;
        else if (exp <= 14500) return 17;
        else if (exp <= 16000) return 18;
        else if (exp <= 18000) return 19;
        else if (exp <= 20000) return 20;
        else if (exp <= 22000) return 21;
        else if (exp <= 24000) return 22;
        else if (exp <= 27000) return 23;
        else if (exp <= 30000) return 24;
        else if (exp <= 33000) return 25;
        else if (exp <= 35000) return 26;
        else if (exp <= 38000) return 27;
        else if (exp <= 42000) return 28;
        else if (exp <= 45000) return 29;
        return 30;
    }

    public int getExpNextLevel() {
        int level = getLevel();
        if (level == 1) return 100;
        if (level == 2) return 300;
        if (level == 3) return 800;
        if (level == 4) return 1500;
        if (level == 5) return 2300;
        if (level == 6) return 3000;
        if (level == 7) return 3800;
        if (level == 8) return 4500;
        if (level == 9) return 5300;
        if (level == 10) return 6000;
        if (level == 11) return 7000;
        if (level == 12) return 8000;
        if (level == 13) return 9000;
        if (level == 14) return 10000;
        if (level == 15) return 11500;
        if (level == 16) return 13000;
        if (level == 17) return 14500;
        if (level == 18) return 16000;
        if (level == 19) return 18000;
        if (level == 20) return 20000;
        if (level == 21) return 22000;
        if (level == 22) return 24000;
        if (level == 23) return 27000;
        if (level == 24) return 30000;
        if (level == 25) return 33000;
        if (level == 26) return 35000;
        if (level == 27) return 38000;
        if (level == 28) return 42000;
        if (level == 29) return 45000;
        return 99999;
    }

    public int getExpCurrentLevel() {
        int level = getLevel();
        if (level == 1) return 0;
        if (level == 2) return 100;
        if (level == 3) return 300;
        if (level == 4) return 800;
        if (level == 5) return 1500;
        if (level == 6) return 2300;
        if (level == 7) return 3000;
        if (level == 8) return 3800;
        if (level == 9) return 4500;
        if (level == 10) return 5300;
        if (level == 11) return 6000;
        if (level == 12) return 7000;
        if (level == 13) return 8000;
        if (level == 14) return 9000;
        if (level == 15) return 10000;
        if (level == 16) return 11500;
        if (level == 17) return 13000;
        if (level == 18) return 14500;
        if (level == 19) return 16000;
        if (level == 20) return 18000;
        if (level == 21) return 20000;
        if (level == 22) return 22000;
        if (level == 23) return 24000;
        if (level == 24) return 27000;
        if (level == 25) return 30000;
        if (level == 26) return 33000;
        if (level == 27) return 35000;
        if (level == 28) return 38000;
        if (level == 29) return 42000;
        return 50000;
    }

    public String getCrownName() {
        return crownName;
    }

    public void setCrownName(String crownName) {
        this.crownName = crownName;
    }

    public Crown getCrown() {

        if (crownName.equals("Người tuyết"))
            return new Crown(username, crownName, R.drawable.snowman_crown, 0);

        if (crownName.equals("Đồng"))
            return new Crown(username, crownName, R.drawable.bronze_crown, 0);
        if (crownName.equals("Bạc"))
            return new Crown(username, crownName, R.drawable.silver_crown, 0);
        if (crownName.equals("Vàng"))
            return new Crown(username, crownName, R.drawable.gold_crown, 0);
        if (crownName.equals("Bạch kim"))
            return new Crown(username, crownName, R.drawable.platinum_crown, 0);
        if (crownName.equals("Kim cương"))
            return new Crown(username, crownName, R.drawable.diamond_crown, 0);
        if (crownName.equals("Kim cương đỏ"))
            return new Crown(username, crownName, R.drawable.red_diamond_crown, 0);

        if (crownName.equals("Bình minh"))
            return new Crown(username, crownName, R.drawable.aurora_crown, 200);
        if (crownName.equals("Hoa hồng"))
            return new Crown(username, crownName, R.drawable.flora_crown, 200);
        if (crownName.equals("Vòng hoa"))
            return new Crown(username, crownName, R.drawable.flower_crown, 200);
        if (crownName.equals("Đá quý"))
            return new Crown(username, crownName, R.drawable.gemini_crown, 200);
        if (crownName.equals("Huyền bí"))
            return new Crown(username, crownName, R.drawable.mystic_crown, 200);
        if (crownName.equals("Neon"))
            return new Crown(username, crownName, R.drawable.neon_crown, 200);
        if (crownName.equals("Lam ngọc"))
            return new Crown(username, crownName, R.drawable.sapphire_crown, 200);
        if (crownName.equals("Mùa hè"))
            return new Crown(username, crownName, R.drawable.summer_crown, 200);
        if (crownName.equals("Lễ hội"))
            return new Crown(username, crownName, R.drawable.vacation_crown, 200);

        return new Crown(username, "snowman", R.drawable.snowman, 0);
    }

}
