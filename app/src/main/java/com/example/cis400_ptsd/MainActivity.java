package com.example.cis400_ptsd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "25296bdf3c294d25a1106de0b93e4278"; // This is my personal id (Tim). Might be changed later.
    private static final String REDIRECT_URI = "com.android.application://callback"; // Might need to be changed
    private SpotifyAppRemote mSpotifyAppRemote;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set our background to be a changing gradient
        ConstraintLayout main_layout = findViewById(R.id.main_layout);
        AnimationDrawable background_drawable = (AnimationDrawable) main_layout.getBackground();
        background_drawable.setEnterFadeDuration(3000);
        background_drawable.setExitFadeDuration(6000);
        background_drawable.start();

        //Instantiate buttons in our Main Activity
        ImageButton reporting_button = findViewById(R.id.reporting_button);
        ImageButton meditation_button = findViewById(R.id.spotify_button);
        ImageButton game_button = findViewById(R.id.game_button);
        ImageButton maps_button = findViewById(R.id.maps_button);
        TextView streak_text = findViewById(R.id.streak_text);

        //Set the event handler for our buttons
        reporting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(Learn.class);}});

        meditation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(MeditationMain.class);}});

        game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(GameMain.class);}});

        maps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {switch_activities(MapMain.class);}});

        //Check streak
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Instant date = Instant.now();
        LocalDate currDate = date.atZone(ZoneId.of(TimeZone.getDefault().getID())).toLocalDate();
        String formattedDate = currDate.format(outputFormatter);

        Map<String, ?> allPref = sharedPref.getAll();

        if(allPref.isEmpty()){
            editor.putInt("streak", 0);
            editor.putString("checkin", formattedDate);
            editor.apply();

            streak_text.setText("Welcome!");
        }  else{
            int streak = (int) allPref.get("streak");
            LocalDate lastDate = LocalDate.parse((String) allPref.get("checkin"));
            LocalDate lowerDate = lastDate.plusDays(1);

            if(lowerDate.compareTo(currDate) == 0){
                streak += 1;
                editor.putInt("streak", streak);
                editor.putString("checkin", formattedDate);
                editor.apply();
            }else if(lowerDate.compareTo(currDate) > 0){
                editor.putInt("streak", 0);
                editor.putString("checkin", formattedDate);
                editor.apply();
            }

            if(streak == 0){
                streak_text.setText("Welcome Back!");
            }else{
                streak_text.setText(String.format(getString(R.string.streak), (int) allPref.get("streak")));
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, CheckIn.newInstance(), "CheckIn").commit();

    }

    @Override
    protected void onStart() { // For Spotify API
        super.onStart();
        // Set the connection parameters for Spotify API
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) { // Print error message to debug console if can't connect
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect. Handle errors here
                    }
                });
    }

    private void connected() { // For Spotify API
        // Then we will write some more code here.
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"); // Plays piano music when ran. If does not work it will go to onFailure method

        mSpotifyAppRemote.getPlayerApi() // log the track title and artist of the song that will be playing
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    @Override
    protected void onStop() { // For Spotify API
        super.onStop();
        // Aaand we will finish off here.
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void switch_activities(Class class_to_switch) {
        Intent switchActivityIntent = new Intent(this, class_to_switch);
        startActivity(switchActivityIntent);
    }
}