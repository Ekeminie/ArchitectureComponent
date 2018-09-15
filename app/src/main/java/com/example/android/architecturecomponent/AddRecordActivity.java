package com.example.android.architecturecomponent;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.android.architecturecomponent.database.AppDatabase;
import com.example.android.architecturecomponent.database.RecordEntity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRecordActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int LEVEL_BEGINNER = 1;
    public static final int LEVEL_INTERMEDIATE = 2;
    public static final int LEVEL_ADVANCED = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddRecordActivity.class.getSimpleName();
    // Fields for views

    @BindView(R.id.editDesignation)
    EditText designationEditText;

    @BindView(R.id.editName)
    EditText nameEditText;

    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    @BindView(R.id.saveButton)
    Button mButton;


    private int mTaskId = DEFAULT_TASK_ID;


    // Member variable for the Database
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ButterKnife.bind(this);

        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
            AddRecordViewModelFactory addRecordViewModelFactory = new AddRecordViewModelFactory(mDb, mTaskId);
            final AddRecordViewModel addRecordViewModel = ViewModelProviders.of(this, addRecordViewModelFactory).get(AddRecordViewModel.class);
            addRecordViewModel.getRecData().observe(this, new Observer<RecordEntity>() {
                @Override
                public void onChanged(@Nullable RecordEntity recordEntity) {
                    addRecordViewModel.getRecData().removeObserver(this);
                    populateUI(recordEntity);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);

    }

    private void initViews() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavedButtonClick();
            }
        });

    }

    private void populateUI(RecordEntity recordEntity){
        if (recordEntity == null){
            return;
        }
        nameEditText.setText(recordEntity.getName());
        designationEditText.setText(recordEntity.getDesignation());
        setLevelOnViews(recordEntity.getLevel());

    }

    private void onSavedButtonClick() {
        String name = nameEditText.getText().toString().trim();
        String designation = designationEditText.getText().toString().trim();
        int level = getLevelFromViews();
        Date date = new Date();

        final RecordEntity recordEntity = new RecordEntity(name, designation, level, date);
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                    if(mTaskId == DEFAULT_TASK_ID){
                        //Insert New Record
                        mDb.recordDoa().insert(recordEntity);
                    }else {
                        //Update a Record
                        recordEntity.setId(mTaskId);
                        mDb.recordDoa().update(recordEntity);
                    }

                    finish();
            }
        });
    }



    public int getLevelFromViews() {
        int level = 0;
        int checkedID = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedID){
            case R.id.radButton1:
                level = LEVEL_BEGINNER;
                break;
            case R.id.radButton2:
                level = LEVEL_INTERMEDIATE;
                break;
            case R.id.radButton3:
                level = LEVEL_ADVANCED;
                break;

        }
            return level;
    }


    public void setLevelOnViews(int level){
        switch (level){
            case LEVEL_BEGINNER:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case LEVEL_INTERMEDIATE:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case LEVEL_ADVANCED:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
                break;

        }
    }
}
