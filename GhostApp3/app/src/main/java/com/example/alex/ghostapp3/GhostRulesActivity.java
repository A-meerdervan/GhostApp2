package com.example.alex.ghostapp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;



public class GhostRulesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_rules);
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Put screen in right language
        TextView tvTheRules = (TextView)findViewById(R.id.TheRulesTV);
        TextView tvRules = (TextView)findViewById(R.id.RulesTV);
        if (Language.equals("dutch")){
            tvTheRules.setText(this.getString(R.string.TheRulesDutch));
            tvRules.setText(this.getString(R.string.RulesDutch));
        }
        else{
            tvTheRules.setText(this.getString(R.string.TheRulesEnglish));
            tvRules.setText(this.getString(R.string.RulesEnglish));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_rules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.Settings) {
            // go to the Settings page.
            startActivity(new Intent(getApplicationContext(), GhostSettingsActivity.class));
            // This activity is not closed, the user will return by pressing the back button from the rules activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
