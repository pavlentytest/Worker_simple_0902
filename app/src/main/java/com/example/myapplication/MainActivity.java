package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private OneTimeWorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(this).enqueue(workRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(
                MainActivity.this, workInfo -> {
                    if(workInfo != null) {
                        Log.d("RRR", "Worker state:"+workInfo.getState());
                        String message = workInfo.getOutputData().getString("keyA");
                        int i = workInfo.getOutputData().getInt("keyB",0);
                        Log.d("RRR","Result: message: "+message+", i: "+i);
                    }
                }
        );
    }
}