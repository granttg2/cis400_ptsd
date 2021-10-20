package com.example.cis400_ptsd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_1);

        //Initialize buttons
        Button red_button = findViewById(R.id.redButton2);
        Button blue_button = findViewById(R.id.blueButton2);
        Button yellow_button = findViewById(R.id.yellowButton2);
        Button green_button = findViewById(R.id.greenButton2);

        //Set the buttons event handlers
        red_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        blue_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        yellow_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        green_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });
    }
}
