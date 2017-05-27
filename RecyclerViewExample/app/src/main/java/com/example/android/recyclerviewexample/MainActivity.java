package com.example.android.recyclerviewexample;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        RecyclerView mRecyclerView;
        HomeAdapter mAdapter = new HomeAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // using linear layout manager and divider
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Drawable dividerDrawable = getApplicationContext().getDrawable(R.drawable.normal_divider);
//        mRecyclerView.addItemDecoration(new LinearDivider(dividerDrawable));
        // using grid layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridDivider(50));
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initData() {
        this.mData = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            this.mData.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View targetView = LayoutInflater.from( MainActivity.this )
                    .inflate(R.layout.recycler_view_item, parent,false);
            return new MyViewHolder(targetView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends ViewHolder {
            TextView tv;
            private MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
