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
    private int startColorGradient;
    private int endColorGradient;
    private int buttonColor;
    private boolean isFingerPrint;

    public UserModel(){}
    public UserModel(String username,String pswrd, String mailAdress,int avatar,int startColorGradient,int endColorGradient,int buttonColor,boolean isFingerPrint){
        this.mailAdress=mailAdress;
        this.username=username;
        this.password=pswrd;
        this.avatar=avatar;
        this.startColorGradient=startColorGradient;
        this.endColorGradient=endColorGradient;
        this.buttonColor=buttonColor;
        this.isFingerPrint=isFingerPrint;
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

    public int getStartColorGradient() {
        return startColorGradient;
    }
    public void setStartColorGradient(int startColorGradient) {
        this.startColorGradient = startColorGradient;
    }

    public int getEndColorGradient() {
        return endColorGradient;
    }
    public void setEndColorGradient(int endColorGradient) {
        this.endColorGradient = endColorGradient;
    }

    public int getButtonColor() {
        return buttonColor;
    }
    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    public boolean isFingerPrint() {
        return isFingerPrint;
    }
    public void setFingerPrint(boolean fingerPrint) {
        isFingerPrint = fingerPrint;
    }




}
