package com.yehigo.eagle.voiceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.yehigo.eagle.voiceapp.DBMS.AsyncDB;
import com.yehigo.eagle.voiceapp.DBMS.AsyncDBListener;
import com.yehigo.eagle.voiceapp.DBMS.Words;

import java.util.List;

public class ListActivity extends AppCompatActivity {


    private RecyclerView _recyclerView;
    private  MyAdapter _adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
          getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                _recyclerView = findViewById(R.id.list);

        (new AsyncDB(getApplicationContext())).getALL(new AsyncDBListener() {
            @Override
            public void OnSuccess(Object object) {
                LinearLayoutManager llm = new LinearLayoutManager(ListActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                _recyclerView.setLayoutManager(llm);
                _adapter = new MyAdapter ((List<Words>) object);
                _recyclerView.setAdapter(_adapter);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _recyclerView.setAdapter(null);
        _adapter = null;
    }
}

