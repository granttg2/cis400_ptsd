package com.example.cis400_ptsd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;



public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "25296bdf3c294d25a1106de0b93e4278"; // This is my personal id (Tim). Might be changed later.
    private static final String REDIRECT_URI = "com.android.application://callback"; // Might need to be changed
    private SpotifyAppRemote mSpotifyAppRemote;

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