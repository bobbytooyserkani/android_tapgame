package com.example.tapgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    // This button saves the score into the database and then moves the user to the Score Board
    Button toScoreBoardButton;
    TextView timerTextView, scoreTextView;
    ImageView pressImageView;


    // I am creating a database reference object to connect my app to
    // the specific database in my firebase project

    FirebaseDatabase mydatabase;
    DatabaseReference myreference;


    int scoreCount = 0;
    String finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toScoreBoardButton =(Button)findViewById(R.id.button_toScoreBoardPage);
        pressImageView=(ImageView)findViewById(R.id.textView_pushbutton);
        scoreTextView=(TextView)findViewById(R.id.textView_scorecount);
        timerTextView=(TextView)findViewById(R.id.textView_timer);



        mydatabase=FirebaseDatabase.getInstance();

        myreference=mydatabase.getReference("players");

        pressImageView.setOnClickListener(this);
        toScoreBoardButton.setOnClickListener(this);

        new CountDownTimer(5000, 1000)
        {

            @Override
            public void onTick(long l)
            {
                timerTextView.setText(""+l/1000);
            }

            @Override
            public void onFinish()
            {
                finalScore = String.valueOf(scoreCount);

                pressImageView.setVisibility(View.GONE);
                toScoreBoardButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.textView_pushbutton:
                scoreCount++;
                scoreTextView.setText(String.valueOf(scoreCount));
                break;
            case R.id.button_toScoreBoardPage:

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                Intent usrIntent = getIntent();
                String usrNameStr = usrIntent.getStringExtra("message_key");



                // Create a hashmap object to insert key/value pairs data into the database
                // The first string below refers to the data type of the key or column name
                // The second string below refers to the data you want to insert into that column
                HashMap<String,String> myhashmap=new HashMap<String, String>();


                // Insert usrNameStr data into the table
                myhashmap.put("usrName",usrNameStr);

                // Insert currentDate data into the table
                myhashmap.put("date",currentDate);

                // Insert score data into the table
                myhashmap.put("score",finalScore);

                // This names the primary key (in this case "customer1), rather than take a messy auto-generated one.
                myreference.child("player1").setValue(myhashmap);


                Toast.makeText(MainActivity2.this,"Database Inserted Successfully",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ScoreBoard.class);
                startActivity(intent);
                break;
        }
    }
}