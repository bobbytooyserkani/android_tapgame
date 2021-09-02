package com.example.tapgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScoreBoard extends AppCompatActivity implements ValueEventListener {

    FirebaseDatabase mydatabase;
    ListView resultlistview;
    List<String> recordlist = new ArrayList<String>();

    //Array Adapter funnels data from the cloud to my mobile app's cache memory.

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        resultlistview=(ListView)findViewById(R.id.listview_result);

        //Connect the app to the database
        mydatabase = FirebaseDatabase.getInstance();

        //Connect the app to the table in the database
        DatabaseReference databaseReference = mydatabase.getReference();

        //Attach a listener to the database reference relating to the players table
        //so when the data in the table changes, I want the output on my screen to change
        //without user refreshing anything.

        //This is my listener
        databaseReference.child("players").addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        //I would like to create a variable that stores all database data

        Iterator<DataSnapshot> records = snapshot.getChildren().iterator();

        while (records.hasNext()) {
            //This is one row of data in my database table
            DataSnapshot record = records.next();

            String id, usrName, date, score;

            //getKey() built in method automatically gets the primary key value of each record
            id = record.getKey();

            usrName = record.child("usrName").getValue().toString();
            score = record.child("score").getValue().toString();
            date = record.child("date").getValue().toString();
            id = record.child("id").getValue().toString();


            recordlist.add("Score: " + score + " UserName: " + usrName + "ID: " + id + " Date: " + date + "\n");

            adapter = new ArrayAdapter<String>(ScoreBoard.this, android.R.layout.simple_expandable_list_item_1, recordlist);

            resultlistview.setAdapter(adapter);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}