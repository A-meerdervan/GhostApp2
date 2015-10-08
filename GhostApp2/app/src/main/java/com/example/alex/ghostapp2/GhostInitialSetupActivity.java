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
import android.widget.Toast;

public class GhostInitialSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        if (prefs.getBoolean("GameStillGoing", false)){
            startActivity(new Intent(getApplicationContext(), GhostGameActivity.class));
            this.finish();
        }
        setContentView(R.layout.activity_ghost_initial_setup);
        String Language = prefs.getString("Language", "Dutch");
        // Change the screen items:

    }

    public void onClickViewRules(View view){
        // go to the rules page.
        startActivity(new Intent(getApplicationContext(), GhostRulesActivity.class));
        // This activity is not closed, the user will return by pressing the back button from the rules activity

    }
    public void onClickLanguageFlag(View view){
        SharedPreferences prefs = this.getSharedPreferences("SaveGame",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (view.getId() == R.id.DutchFlag){
            Log.d("xzzr", "in NL vlag optie" + view.getId());
            Log.d("xzzr", "in NL vlag optie" + R.id.DutchFlag);
            // zet de taal op nederlands
            editor.putString("Language", "dutch");
        }
        else{
            Log.d("xzzr", "in Eng vlag optie" + view.getId());
            // zet de taal op engels
            editor.putString("Language", "english");
        }
        editor.commit();
        // Show user the language changed
        Toast.makeText(getApplicationContext(), "Language changed", Toast.LENGTH_LONG).show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
