package com.example.android.recyclerviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private List<String> data;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    HomeAdapter(Context _context, List<String> _data) {
        this.context = _context;
        this.data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View targetView = LayoutInflater.from(this.context)
                .inflate(R.layout.recycler_view_item, parent,false);
        MyViewHolder holder = new MyViewHolder(targetView);
        targetView.setOnClickListener(this);
        targetView.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(this.data.get(position));
    }

    //点击事件回调
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener!= null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
    void addDataInFirst() {
        this.data.add(0, "Insert One");
        notifyItemInserted(0);
        notifyItemRangeChanged(1, this.data.size());
    }

    void removeDataInFirst() {
        this.data.remove(0);
        notifyItemRemoved(0);
        notifyItemRangeChanged(0, this.data.size());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        private MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }

}
