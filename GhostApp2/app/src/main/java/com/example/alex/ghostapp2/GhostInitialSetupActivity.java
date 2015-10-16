package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class GhostInitialSetupActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if the app was closed during a game, continue to game activity
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        if (prefs.getBoolean("GameStillGoing", false)){
            startActivity(new Intent(getApplicationContext(), GhostGameActivity.class));
            this.finish();
        }
        setContentView(R.layout.activity_ghost_initial_setup);
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView ChangeLanguage = (TextView) findViewById(R.id.LanguageTV);
        Button Rules = (Button) findViewById(R.id.ViewRulesButton);
        Button Continue = (Button) findViewById(R.id.ContinueButton);

        if (Language.equals("dutch")) {
            ChangeLanguage.setText("Kies uw taal");
            Rules.setText("Spelregels");
            Continue.setText("Volgende");
        } else {
            ChangeLanguage.setText("Choose language");
            Rules.setText("View the rules");
            Continue.setText("Continue");
        }
    }

    public void onClickViewRules(View view){
        // go to the rules page.
        startActivity(new Intent(getApplicationContext(), GhostRulesActivity.class));
        // This activity is not closed, the user will return by pressing the back button from the rules activity

    }

    // Set the Language that matches the flag that was clicked
    public void onClickLanguageFlag(View view) {
        SharedPreferences prefs = this.getSharedPreferences("SaveGame", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (view.getId() == R.id.DutchFlag) {
            editor.putString("Language", "dutch");
            editor.commit();
            // Show user the language changed
            Toast.makeText(getApplicationContext(), "De taal is nu Nederlands", Toast.LENGTH_LONG).show();
            // Change screen items to current language
            setViewsToLanguage();
        } else {
            editor.putString("Language", "english");
            editor.commit();
            // Show user the language changed
            Toast.makeText(getApplicationContext(), "The language is now english", Toast.LENGTH_LONG).show();
            setViewsToLanguage();
        }
    }

    public void onClickContinue(View view){
        // go to the next activity, (setup)
        startActivity(new Intent(getApplicationContext(), GhostSetupActivity.class));
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_initial_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        switch (id)
//        {
//            case R.id.action_settings:
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.Settings) {
            // go to the Settings page.
            startActivity(new Intent(getApplicationContext(), GhostSettingsActivity.class));
            // This activity is not closed, the user will return by pressing the back button from the rules activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
