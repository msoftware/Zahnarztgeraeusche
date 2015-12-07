package de.naturalnet.zahnarztgeraeusche;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    Hashtable<String, MediaPlayer> players;

    ToggleButton btnDrill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        players = new Hashtable<String, MediaPlayer>();

        for (Field f : R.raw.class.getFields()) {
            try {
                players.put(f.getName(), MediaPlayer.create(this, f.getInt(f)));
            } catch (IllegalAccessException e) {
                // FIXME Do something useful
            }
        }

        btnDrill = (ToggleButton) findViewById(R.id.btnDrill);
        btnDrill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnDrill.isChecked()) {
                    players.get("drill").setLooping(true);
                    players.get("drill").start();
                } else {
                    players.get("drill").pause();
                    players.get("drill").seekTo(0);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
