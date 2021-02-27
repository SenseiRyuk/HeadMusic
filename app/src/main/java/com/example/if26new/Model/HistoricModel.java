package com.example.if26new.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(foreignKeys = @ForeignKey(entity = UserModel.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE))
public class HistoricModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "userId")
    private int userId;
    private String trackID;

    public HistoricModel(){}
    public HistoricModel(String id,int userId){
        this.trackID=id;
        this.userId=userId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTrackID() {
        return trackID;
    }
    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

}
