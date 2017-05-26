package com.example.android.listviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // animal names array
    String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    // animal images array
    int[] animalImages = {R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.cat};
    ListView simpleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.simpleListView = (ListView) findViewById(R.id.simple_list_view);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < animalImages.length; i += 1) {
            // create a hashMap to store the data in key value pair
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", this.animalNames[i]);
            hashMap.put("image", this.animalImages[i] + "");
            //add the hashMap into arrayList
            arrayList.add(hashMap);
        }
        // string array
        String[] from = {"name", "image"};
        // int array of views id's
        int[] to = {R.id.textView, R.id.imageView};
        // Create object and set the parameters for simpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_view_item, from, to);
        // sets the adapter for listView
        simpleListView.setAdapter(simpleAdapter);
        // perform listView item click event
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the selected image in toast according to position
                CharSequence text = "This is a " + animalNames[i];
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
