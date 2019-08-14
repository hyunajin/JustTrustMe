package com.example.asus.justtrustme;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Siren extends AppCompatActivity {

    private MediaPlayer mp;

    Button Siren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);

        mp =  MediaPlayer.create(com.example.asus.justtrustme.Siren.this, R.raw.siren);
        mp.setLooping( true );
        final Button Siren = (Button)findViewById(R.id.siren);

        Siren.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mp != null) {
                    if (mp.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.seekTo(0);
                        mp.start();
                    }
                }
            }
        });

    }

}
