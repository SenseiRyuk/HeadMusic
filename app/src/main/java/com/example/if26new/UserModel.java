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
   //private ArrayList<ArrayList<String>> playlist=new ArrayList();
    /*ArrayList<String> musics=new ArrayList();
        musics.add("This is my pen");
        musics.add("This is my book ");
        musics.add("This seifjava2's Program !");
        playlist.add(0,l);*/
    
    public UserModel(String username,String pswrd, String mailAdress){
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
