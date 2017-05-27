package com.example.android.recyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mData;
    private RecyclerView mRecyclerView;
    HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        this.mAdapter = new HomeAdapter(getApplicationContext(), mData);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // using linear layout manager and divider
//        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Drawable dividerDrawable = getApplicationContext().getDrawable(R.drawable.normal_divider);
//        this.mRecyclerView.addItemDecoration(new LinearDivider(dividerDrawable));
        // using grid layout manager
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        this.mRecyclerView.addItemDecoration(new GridDivider(50));
        this.mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        this.mRecyclerView.setAdapter(this.mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.add_item:
                this.mAdapter.addDataInFirst();
                break;
            case R.id.delete_item:
                this.mAdapter.removeDataInFirst();
                break;
        }
        return true;
    }

    protected void initData() {
        this.mData = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            this.mData.add("" + (char) i);
        }
    }
}
