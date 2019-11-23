package com.example.mynots;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   Button btnaddnote;
   TextView title;
   TextView note;
    TextView mytitle;
    TextView mynote;
    Not mynotes;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Not> arrayList;
    ListView listView;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.not_list_view_id);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Notes");
        btnaddnote=findViewById(R.id.btn_add_new_not_id);
        btnaddnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdaialognotes();
            }
        });
        /////////
        arrayList=new ArrayList<>();
        //////////////////////////////////////
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           Not mynotes=arrayList.get(position);
               String titleshow=mynotes.title;
               String noteshow=mynotes.note;
           Intent intent=new Intent(MainActivity.this,ShowNote.class);
           intent.putExtra("title_key",titleshow);
           intent.putExtra("note_key",noteshow);
           startActivity(intent);

           }
       });
       ///////////////////////////////////Long click kistview
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //لاظهار الdaialog وعرض عنوان الملاحظه ومحتواها
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View view2=LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_deleet_and_update,null);
                builder.setView(view2);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                  mynotes=arrayList.get(position);
                String titledeletupdate=mynotes.title;
                String notedeletupdate=mynotes.note;
                 mytitle=view2.findViewById(R.id.title_delletandupdate_id);
                 mynote=view2.findViewById(R.id.note_delletandupdate_id);
                Button btn_dell=view2.findViewById(R.id.btn_dellet);
                Button btn_update=view2.findViewById(R.id.btn_update);
                mytitle.setText(titledeletupdate);
                mynote.setText(notedeletupdate);
                ////////////////////////لحذف الملاحظه
                btn_dell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        arrayList.clear();
                        myRef.child(mynotes.id).removeValue();
                        alertDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Dellet Successfull", Toast.LENGTH_SHORT).show();

                    }
                });
                /////////////////////////////////
                ////////////////////////لتعديل الملاحظه
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference childRef=myRef.child(mynotes.id);
                        Calendar calendar=Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                        String datetime=simpleDateFormat.format(calendar.getTime());
                        Map<String,String>map=new HashMap<>();
                        map.put("title",mytitle.getText().toString());
                        map.put("note",mynote.getText().toString());
                        map.put("Date",datetime.toString());
                        map.put("id",mynotes.id);
                        childRef.setValue(map);
                        alertDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                    }
                });
                ///////////////////////////
                return false;
            }
        });
        ///////////////////////////////////////////
     ///////////////////////////////////////////
    }
    ////////////////////////////////دي تاني داله بتتنفذ بعد الoncreate

    @Override
    protected void onStart() {
        super.onStart();
        ///////////
        // Read from the database
        arrayList.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
            //    String value = dataSnapshot.getValue(String.class);
                arrayList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    String title=ds.child("title").getValue().toString();
                    String note=ds.child("note").getValue().toString();
                    String Date=ds.child("Date").getValue().toString();
                    String ID=ds.child("id").getValue().toString();
                    arrayList.add(0,new Not(ID,title,note,Date));
                }
                newAdapter adapter=new newAdapter(MainActivity.this,arrayList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    /////////////////////
    public void showdaialognotes(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View view1=LayoutInflater.from(MainActivity.this).inflate(R.layout.add_new,null);
        Button btn_save=view1.findViewById(R.id.btn_save_note);
         title=view1.findViewById(R.id.title_edittext_id);
        note=view1.findViewById(R.id.note_edittext_id);
        ////////////////////////////////////
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                String x,y;
                x=title.getText().toString();
                y=note.getText().toString();
            if(x.isEmpty()&&y.isEmpty()){
                Toast.makeText(MainActivity.this, "Pleas Enter Title and Note Message", Toast.LENGTH_SHORT).show();

            }else{
                String id=myRef.push().getKey();
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                String datetime=simpleDateFormat.format(calendar.getTime());
               Map<String,String>map=new HashMap<>();
                map.put("title",title.getText().toString());
                map.put("note",note.getText().toString());
                map.put("Date",datetime.toString());
                map.put("id",id);
                myRef.child(id).setValue(map);
                title.setText("");
                note.setText("");
                arrayList.clear();

            }
            }
        });
        ////////////////////////////////
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //////////////
}
