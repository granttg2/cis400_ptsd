package com.example.cis400_ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set our background to be a changing gradient
        LinearLayout main_layout = findViewById(R.id.main_layout);
        AnimationDrawable background_drawable = (AnimationDrawable) main_layout.getBackground();
        background_drawable.setEnterFadeDuration(3000);
        background_drawable.setExitFadeDuration(6000);
        background_drawable.start();

        //Instantiate buttons in our Main Activity
        ImageButton reporting_button = findViewById(R.id.reporting_button);
        ImageButton meditation_button = findViewById(R.id.spotify_button);
        ImageButton game_button = findViewById(R.id.game_button);
        ImageButton maps_button = findViewById(R.id.maps_button);

        //Set the event handler for our buttons
        reporting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(CheckListMain.class);}});

        meditation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(GameMain.class);}});

        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(GameMain.class);}});

        maps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(MapMain.class);}});

    }

    private void switch_activities(Class class_to_switch) {
        Intent switchActivityIntent = new Intent(this, class_to_switch);
        startActivity(switchActivityIntent);
    }
}