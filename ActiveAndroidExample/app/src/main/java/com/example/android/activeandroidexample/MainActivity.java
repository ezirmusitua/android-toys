package com.example.android.activeandroidexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyListAdapter noteListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        try {
            this.initListView();
        } catch (Exception e) {
            Log.e("JError: ", e.toString());
        }
    }

    private void initListView() {
        ListView simpleListView = (ListView) findViewById(R.id.note_list);
        List<Note> existedNotes = new Select().from(Note.class).execute();
        this.noteListAdapter = new MyListAdapter(getApplicationContext(), existedNotes);
        simpleListView.setAdapter(this.noteListAdapter);
    }

    public void insertNote(View view) {
        TextView tv_content = (TextView) findViewById(R.id.edit_content);
        String content = tv_content.getText() + "";
        Note noteToInsert = new Note(content, "jferroal");
        noteToInsert.save();
        tv_content.setText("");
        this.noteListAdapter.add(noteToInsert);
        this.noteListAdapter.notifyDataSetChanged();
    }
}
