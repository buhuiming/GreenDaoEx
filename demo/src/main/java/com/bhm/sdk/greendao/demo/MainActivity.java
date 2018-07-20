package com.bhm.sdk.greendao.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bhm.sdk.greendao.PG;
import com.bhm.sdk.greendao.demo.entity.TestEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestEntity entity = new TestEntity();
        entity.setUsername("sss");
        PG.convertParcelable(entity);
    }
}
