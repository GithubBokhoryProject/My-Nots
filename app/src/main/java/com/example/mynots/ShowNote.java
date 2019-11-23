package com.example.mynots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowNote extends AppCompatActivity {
      TextView txt_title,txt_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        txt_title=findViewById(R.id.txt_title_show_id);
        txt_note=findViewById(R.id.txt_note_show_id);
        ///////////////////////////
        String title=getIntent().getStringExtra("title_key");
        String note=getIntent().getStringExtra("note_key");
        txt_title.setText(title);
        txt_note.setText(note);

    }
}
