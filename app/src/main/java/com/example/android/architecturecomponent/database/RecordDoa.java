package com.example.android.architecturecomponent.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Delight on 09/09/2018.
 */

@Dao
public interface RecordDoa {

    @Query("SELECT * FROM record ORDER BY level")
    LiveData<List<RecordEntity>> loadAllRecords();


    @Query("SELECT * FROM record WHERE id= :id")
    LiveData<RecordEntity> loadRecordById(int id);


    @Insert
    void insert(RecordEntity recordEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(RecordEntity recordEntity);

    @Delete
    void delete(RecordEntity recordEntity);



}
