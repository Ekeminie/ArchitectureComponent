package com.example.android.architecturecomponent;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.architecturecomponent.database.AppDatabase;
import com.example.android.architecturecomponent.database.RecordEntity;

import java.util.List;

/**
 * Created by Delight on 09/09/2018.
 */

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<RecordEntity>>record;

    public MainViewModel( Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        record = database.recordDoa().loadAllRecords();


    }


    public LiveData<List<RecordEntity>> getRecord(){
        return record;
    }
}
