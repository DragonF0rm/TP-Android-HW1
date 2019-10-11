package com.example.tp_android_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tp_android_hw1.ListFragment.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO restore activity state
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            int columnCount = getResources().getInteger(R.integer.listColumnCount);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,ListFragment.newInstance(columnCount))
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO save activity state to bundle
    }
}
