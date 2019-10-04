package com.example.if26new;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {
    @PrimaryKey
    private String username;
    private String password;
   //private ArrayList<ArrayList<String>> playlist=new ArrayList();
    /*ArrayList<String> musics=new ArrayList();
        musics.add("This is my pen");
        musics.add("This is my book ");
        musics.add("This seifjava2's Program !");
        playlist.add(0,l);*/
    
    public UserModel(String username,String pswrd){
        this.username=username;
        this.password=pswrd;
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
