package com.example.cis400_ptsd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_1);

        //Initialize buttons
        Button red_button = findViewById(R.id.redButton);
        Button blue_button = findViewById(R.id.blueButton);
        Button yellow_button = findViewById(R.id.yellowButton);
        Button green_button = findViewById(R.id.greenButton);

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
