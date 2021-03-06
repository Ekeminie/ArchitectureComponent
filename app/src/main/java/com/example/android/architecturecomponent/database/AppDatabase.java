package com.example.android.architecturecomponent.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * Created by Delight on 09/09/2018.
 */

@Database(entities = {RecordEntity.class}, version = 1, exportSchema =false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "record_list";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }

        public abstract RecordDoa recordDoa();
}
