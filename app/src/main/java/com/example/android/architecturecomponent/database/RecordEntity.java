package com.example.android.architecturecomponent.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Delight on 09/09/2018.
 */

@Entity(tableName = "record")
public class RecordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String designation;

    private int level;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public RecordEntity(String name, String designation, int level, Date updatedAt) {
        this.name = name;
        this.designation = designation;
        this.level = level;
        this.updatedAt = updatedAt;
    }

    public RecordEntity(int id, String name, String designation, int level, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.level = level;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
