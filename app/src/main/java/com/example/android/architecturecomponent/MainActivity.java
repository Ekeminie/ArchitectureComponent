package com.example.android.architecturecomponent;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.architecturecomponent.database.AppDatabase;
import com.example.android.architecturecomponent.database.RecordEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements RecordAdapter.ItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecordAdapter mRecordAdapter;
    private AppDatabase mDb;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecordAdapter = new RecordAdapter(this, this);
        mRecyclerView.setAdapter(mRecordAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                    int position = viewHolder.getAdapterPosition();
                        List<RecordEntity> recordEntities =mRecordAdapter.getmRecordEntries();
                        mDb.recordDoa().delete(recordEntities.get(position));
                    }
                });
                    }
                }).attachToRecyclerView(mRecyclerView);


        setupViewModel();


    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecord().observe(this, new Observer<List<RecordEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecordEntity> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mRecordAdapter.setmRecordEntries(taskEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
        intent.putExtra(AddRecordActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }
}
