package com.example.android.architecturecomponent;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.architecturecomponent.database.AppDatabase;

/**
 * Created by Delight on 09/09/2018.
 */

public class AddRecordViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mTaskId;

    public AddRecordViewModelFactory(AppDatabase mDb, int mTaskId) {
        this.mDb = mDb;
        this.mTaskId = mTaskId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddRecordViewModel(mDb, mTaskId);
    }
}
