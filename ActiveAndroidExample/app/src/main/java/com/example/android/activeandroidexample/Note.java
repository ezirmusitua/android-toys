package com.example.android.activeandroidexample;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="note")
public class Note extends Model {
    @Column(name="content", index=true)
    public String content;
    @Column(name="author", index=true)
    public String author;
    public Note() {
        super();
    }
    public Note(String content, String author) {
        super();
        this.content = content;
        this.author = author;
    }
}
