package com.rikkei.training.appadmin_vutruonggiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }
}