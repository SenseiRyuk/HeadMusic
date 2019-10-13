package com.example.if26new;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {
    @PrimaryKey
    private int id;
    private String mailAdress;
    private String username;
    private String password;

    public UserModel(){}
    public UserModel(int id,String username,String pswrd, String mailAdress){
        this.id=id;
        this.mailAdress=mailAdress;
        this.username=username;
        this.password=pswrd;
    }


    public String getMailAdress() {
        return mailAdress;
    }
    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }
    public String getUserName(){
        return this.username;
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

}
