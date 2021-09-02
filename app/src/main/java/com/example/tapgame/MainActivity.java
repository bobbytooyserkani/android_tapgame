package com.example.tapgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button button_play;
    EditText editText_usrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play=(Button)findViewById(R.id.button_play);
        editText_usrName=(EditText)findViewById(R.id.editText_usrName);

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String usrNameStr = editText_usrName.getText().toString();

                Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("message_key",usrNameStr);

                startActivity(intent);
            }
        });
    }
}