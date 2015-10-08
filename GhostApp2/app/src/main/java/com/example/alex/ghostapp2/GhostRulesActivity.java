package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class GhostRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_rules);
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Put screen in right language
        TextView tvTheRules = (TextView)findViewById(R.id.TheRulesTV);
        TextView tvRules = (TextView)findViewById(R.id.RulesTV);
        if (Language.equals("dutch")){
            // Set the views in dutch
            tvTheRules.setText(this.getString(R.string.TheRulesDutch));
            tvRules.setText(this.getString(R.string.RulesDutch));
        }
        else{
            // Set the views in English
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
