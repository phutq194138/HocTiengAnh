package com.example.hoctienganh;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "crown")
public class Crown {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String name;
    private String idResource;
    private int price;

    public Crown(String username, String name, String idResource, int price) {
        this.username = username;
        this.name = name;
        this.idResource = idResource;
        this.price = price;
    }

    public Crown(String username, String name, int idResource, int price) {
        this.username = username;
        this.name = name;
        this.idResource = String.valueOf(idResource);
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIdResource() {
        return idResource;
    }

    public void setIdResource(String idResource) {
        this.idResource = idResource;
    }

    public int getIdImage(){
        return Integer.parseInt(idResource);
    }
}
