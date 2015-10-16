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
    public Context context;

    // The constructor
    public Players(Context contekst) {
        context = contekst;
        fillFromFile();
    }

/*
    public void setCurrentPlayers(String name1, String name2){
        Player Player1 = new Player(name1);
        Player Player2 = new Player(name2);

        PlayersArray.add(Player1);
        PlayersArray.add(Player2);
        CurrentPlayer1 = PlayersArray
                getPlayer(Player1);
    }
*/

    public void setCurrentPlayers(String name1, String name2){
        Player Player1 = new Player(name1);
        Player Player2 = new Player(name2);

        // Set the players to current players
        // Player 1:
        if(PlayersArray.contains(Player1)){
            int Index = getPlayerIndex(Player1);
            CurrentPlayer1 = PlayersArray.get(Index);
        }
        else{
            CurrentPlayer1 = Player1;
            PlayersArray.add(Player1);
        }
        // Player 2:
        if(PlayersArray.contains(Player2)){
            int Index = getPlayerIndex(Player2);
            CurrentPlayer2 = PlayersArray.get(Index);
        }
        else{
            CurrentPlayer2 = Player2;
            PlayersArray.add(Player2);
        }
    }

    private int getPlayerIndex(Player player) {
        for(int i = 0; i < PlayersArray.size(); i++){
            if (PlayersArray.get(i).equals(player)){
                return i;
            }
        }
        // if not found, return -1 so the app crashes, the functon must only be used when the player is indeed in the Array
        return -1;
    }

    public void removeUserHistory() {
        CurrentPlayer1 = new Player();
        CurrentPlayer2 = new Player();
        PlayersArray.clear();
        writeDataToFile(context);
    }

    // A bubble sort style algorithm is used to sort the PlayerArray on scores (High to low)
    // The number of players is relatively small so a more efficient algorithm is not required.
    public void sortArrayOnHighScore(){

        int ArraySize = PlayersArray.size();
        Player TempPlayer;

        for(int i = 0; i < ArraySize; i++){
            for(int j = 1; j < (ArraySize-i); j++){
                if(PlayersArray.get(j - 1).Score < PlayersArray.get(j).Score){
                    //swap the elements
                    TempPlayer = PlayersArray.get(j - 1);
                    PlayersArray.set(j - 1, PlayersArray.get(j));
                    PlayersArray.set(j,TempPlayer);
                }
            }
        }
        writeDataToFile(context);
    }

    public void fillFromFile(){
        SharedPreferences prefs = context.getSharedPreferences("SavePlayers", Context.MODE_PRIVATE);
        // Fill the current players
        CurrentPlayer1.Name = prefs.getString("CurPlay1Name", CurrentPlayer1.Name);
        CurrentPlayer2.Name = prefs.getString("CurPlay2Name", CurrentPlayer2.Name);
        CurrentPlayer1.Score = prefs.getInt("CurPlay1Int", CurrentPlayer1.Score);
        CurrentPlayer2.Score = prefs.getInt("CurPlay2Int", CurrentPlayer2.Score);

        // Fill the Array
        PlayersArray.clear();
        int ArraySize = prefs.getInt("ArraySize", 0);
        for(int i = 0; i < ArraySize; i++){
            String Name = prefs.getString("PlayerName" + i, "uninitialized");
            int Score = prefs.getInt("PlayerScore" + i, 0);
            PlayersArray.add(new Player(Name, Score));
        }
        Log.d("xzzr", "ik kom in fill from file" + CurrentPlayer1.Name + CurrentPlayer1.Score);
    }

    public void writeDataToFile(Context context){
        SharedPreferences prefs = context.getSharedPreferences("SavePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // Write the data for the CurrentPlayers
        editor.putString("CurPlay1Name", CurrentPlayer1.Name);
        editor.putString("CurPlay2Name", CurrentPlayer2.Name);
        editor.putInt("CurPlay1Int", CurrentPlayer1.Score);
        editor.putInt("CurPlay2Int", CurrentPlayer2.Score);
        // Write the data for the Array
        editor.putInt("ArraySize", PlayersArray.size());
        for(int i = 0; i < PlayersArray.size(); i++){
            editor.putString("PlayerName" + i, PlayersArray.get(i).Name);
            editor.putInt("PlayerScore" + i, PlayersArray.get(i).Score);
        }

        editor.commit();
        Log.d("xzzr", "ik kom in write to file");
    }


    public void player2Won() {
        int Index1 = getPlayerIndex(CurrentPlayer1);
        int Index2 = getPlayerIndex(CurrentPlayer2);
        PlayersArray.get(Index1).decreaseScore();
        PlayersArray.get(Index2).increaseScore();
    }

    public void player1Won(){
        int Index1 = getPlayerIndex(CurrentPlayer1);
        int Index2 = getPlayerIndex(CurrentPlayer2);
        PlayersArray.get(Index1).increaseScore();
        PlayersArray.get(Index2).decreaseScore();
    }
}
