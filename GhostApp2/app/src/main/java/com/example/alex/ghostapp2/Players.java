package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 7-10-2015.
 */
public class Players implements java.io.Serializable{

    public ArrayList<Player> PlayersArray = new ArrayList<>();
    public Player CurrentPlayer1 = new Player();
    public Player CurrentPlayer2= new Player();
    public Player PreviousPlayer1= new Player();
    public Player PreviousPlayer2= new Player();

    // The constructor
    public Players(Context contekstt) {
        fillFromFile(contekstt);
    }


    public void setCurrentPlayers(String name1, String name2){
        if(theNameIsNew(name1)){
            // add the name to players
            CurrentPlayer1 = new Player(name1);
            //PlayersArray.add(CurrentPlayer1);
        }
        else{
            // Take the previous one, from the array and copy it to currentplayer.
        }
        if(theNameIsNew(name2)){
            // add the name to players
            CurrentPlayer2 = new Player(name2);
            //PlayersArray.add(CurrentPlayer2);
        }
        else{
            // Take the previous one, from the array and copy it to currentplayer.
        }
    }

    public boolean theNameIsNew(String name){
        return true;
    }

    public void addPlayer(Player player) {
        PlayersArray.add(player);
    }

    public void RemoveAllPlayers() {
        PlayersArray.clear();
    }

    // public void SortArrayOnHighScore(){
    //}

    public void fillFromFile(Context context){
        SharedPreferences prefs = context.getSharedPreferences("SavePlayers", Context.MODE_PRIVATE);
        CurrentPlayer1.Name = prefs.getString("CurPlay1Name", CurrentPlayer1.Name);
        CurrentPlayer2.Name = prefs.getString("CurPlay2Name", CurrentPlayer2.Name);
        CurrentPlayer1.Score = prefs.getInt("CurPlay1Int", CurrentPlayer1.Score);
        CurrentPlayer2.Score = prefs.getInt("CurPlay2Int", CurrentPlayer2.Score);
        Log.d("xzzr", "ik kom in fill from file" + CurrentPlayer1.Name + CurrentPlayer1.Score);
    }

    public void writeDataToFile(Context context){
        SharedPreferences prefs = context.getSharedPreferences("SavePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // Write the data
        editor.putString("CurPlay1Name", CurrentPlayer1.Name);
        editor.putInt("CurPlay1Int", CurrentPlayer1.Score);
        editor.putString("CurPlay2Name", CurrentPlayer2.Name);
        editor.putInt("CurPlay2Int", CurrentPlayer2.Score);

        editor.commit();
        Log.d("xzzr", "ik kom in write to file");
    }



}
