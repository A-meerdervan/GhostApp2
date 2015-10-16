package com.example.alex.ghostapp4;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex van der Meer
 * Student number: 10400958
 * on 12-10-2015.
 *
 * This class is used to keep the user history
 * it has a method to fill itself from the database
 * For the rest it does player administration, adding new players to the database
 * for use in the highscores activity it has a method to sort the players on Score
 *
 *
 */
public class Players implements java.io.Serializable{

    public List<Player> PlayersArray = new ArrayList<>();
    public Player CurrentPlayer1 = new Player();
    public Player CurrentPlayer2= new Player();
    public Context context;
    public DataBaseHandler dataBaseHandler;

    // The constructor
    public Players(Context contekst) {
        context = contekst;
        dataBaseHandler = new DataBaseHandler(context);
        fillFromDataBase();
    }

    public void setCurrentPlayers(String name1, String name2){
        Player Player1 = dataBaseHandler.readPlayerByName(name1);
        Player Player2 = dataBaseHandler.readPlayerByName(name2);
        // Set the players to current players
        // Player 1:
        if(Player1 == null){
            Player1 = new Player(name1);
            CurrentPlayer1 = Player1;
            dataBaseHandler.createPlayer(Player1);
        }
        else{ // The player is already in the database
            CurrentPlayer1 = Player1;
            // place player 1 at the last table entry (This makes it possible to identify the Previous players in next game
            dataBaseHandler.deletePlayer(Player1);
            dataBaseHandler.createPlayer(Player1);
        }
        // Player 2:
        if(Player2 == null){
            Player2 = new Player(name2);
            CurrentPlayer2 = Player2;
            dataBaseHandler.createPlayer(Player2);
        }
        else{ // The player is already in the database
            CurrentPlayer2 = Player2;
            // place player 2 at the last table entry (This makes it possible to identify the Previous players in next game
            dataBaseHandler.deletePlayer(Player2);
            dataBaseHandler.createPlayer(Player2);
        }
    }

    public void removeUserHistory() {
        CurrentPlayer1 = new Player();
        CurrentPlayer2 = new Player();
        PlayersArray.clear();
        dataBaseHandler.deleteAllHistory();
    }

    // A bubble sort style algorithm is used to sort the PlayerArray on scores (High to low)
    // The number of players is relatively small so a more efficient algorithm is not required.
    public List<Player> sortArrayOnHighScore(){

        List<Player> SortedArray = PlayersArray;
        int ArraySize = PlayersArray.size();
        Player TempPlayer;

        for(int i = 0; i < ArraySize; i++){
            for(int j = 1; j < (ArraySize-i); j++){
                if(SortedArray.get(j - 1).Score < SortedArray.get(j).Score){
                    //swap the elements
                    TempPlayer = SortedArray.get(j - 1);
                    SortedArray.set(j - 1, SortedArray.get(j));
                    SortedArray.set(j,TempPlayer);
                }
            }
        }
        return SortedArray;
    }

    public void player2Won() {
        CurrentPlayer1.decreaseScore();
        CurrentPlayer2.increaseScore();
        dataBaseHandler.updatePlayer(CurrentPlayer1);
        dataBaseHandler.updatePlayer(CurrentPlayer2);
    }

    public void player1Won(){
        CurrentPlayer1.increaseScore();
        CurrentPlayer2.decreaseScore();
        dataBaseHandler.updatePlayer(CurrentPlayer1);
        dataBaseHandler.updatePlayer(CurrentPlayer2);
    }

    public void fillFromDataBase(){
        // Fill the Array from the database
        PlayersArray = dataBaseHandler.getAllPlayers();
        // Fill the current players
        if (PlayersArray.size() == 0){
            CurrentPlayer1 = new Player();
            CurrentPlayer2 = new Player();
        }
        else{
            // The most recent players are always put at the end of the DataBase table
            int IndexPlayer1 = PlayersArray.size() - 2;
            int IndexPlayer2 = PlayersArray.size() - 1;
            CurrentPlayer1 = PlayersArray.get(IndexPlayer1);
            CurrentPlayer2 = PlayersArray.get(IndexPlayer2);
        }
    }
}
