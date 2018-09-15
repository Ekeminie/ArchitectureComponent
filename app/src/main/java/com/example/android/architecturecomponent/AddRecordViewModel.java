package com.example.android.architecturecomponent;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.architecturecomponent.database.AppDatabase;
import com.example.android.architecturecomponent.database.RecordEntity;

import java.util.List;

/**
 * Created by Delight on 09/09/2018.
 */

public class AddRecordViewModel extends ViewModel {

    private LiveData<RecordEntity> recData;


    public AddRecordViewModel(AppDatabase database, int taskId) {
         recData = database.recordDoa().loadRecordById(taskId);
    }

    public LiveData<RecordEntity> getRecData() {
        return recData;
    }
}
