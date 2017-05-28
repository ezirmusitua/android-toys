package com.example.android.activeandroidexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


class MyListAdapter extends BaseAdapter {
    private List<Note> _data;
    private LayoutInflater _inflater;

    MyListAdapter(Context context, List<Note> data) {
        this._inflater = LayoutInflater.from(context);
        this._data = data;
    }

    public void add(Note note) {
        this._data.add(note);
    }

    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return this._data.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
        return this._data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this._inflater.inflate(R.layout.note_list_item, null);
        }

        TextView content = (TextView) convertView.findViewById(R.id.note_list_item_title);
        content.setText(this._data.get(position).content);
        TextView author = (TextView) convertView.findViewById(R.id.note_list_item_author);
        author.setText(this._data.get(position).author);
        return convertView;
    }

}
