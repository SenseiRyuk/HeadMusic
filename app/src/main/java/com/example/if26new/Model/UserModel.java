package com.example.if26new.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String mailAdress;
    private String username;
    private String password;
    private int avatar;

    public UserModel(){}
    public UserModel(String username,String pswrd, String mailAdress,int avatar){
        this.mailAdress=mailAdress;
        this.username=username;
        this.password=pswrd;
        this.avatar=avatar;
    }


    public String getMailAdress() {
        return mailAdress;
    }
    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUsername() {
        return username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAvatar() {
        return avatar;
    }
    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }


}
