package com.example.mynots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class newAdapter extends ArrayAdapter<Not> {
    Context context;
    public newAdapter(Context context, List<Not> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.raw,parent,false);
        TextView title=convertView.findViewById(R.id.txt_title_id);
       // TextView note=convertView.findViewById(R.id.txt_note_id);
        TextView date=convertView.findViewById(R.id.txt_date_id);
        Not convertNote=getItem(position);
        title.setText(convertNote.getTitle());
      //  note.setText(convertNote.getNote());
        date.setText(convertNote.getTimestamp());
        return convertView;
    }
}
