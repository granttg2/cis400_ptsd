package com.example.cis400_ptsd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameMain extends Activity {
    //method that creates activity instance
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        //Initialize the buttons from the main layout
        Button game_1_button = findViewById(R.id.game_1_button);
        Button game_2_button = findViewById(R.id.game_2_button);

        //Set the event handlers for the buttons on the main page
        game_1_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(GameMain.this, Game1.class));
            }
        });

        game_2_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(GameMain.this, Game2.class));
            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }



}
