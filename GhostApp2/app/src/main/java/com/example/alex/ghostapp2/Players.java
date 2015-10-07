package com.example.alex.ghostapp2;

import java.util.ArrayList;

/**
 * Created by Alex on 7-10-2015.
 */
public class Players {

    public ArrayList<Player> PlayersArray = new ArrayList<>();
    public Player CurrentPlayer1;
    public Player CurrentPlayer2;
    public Player PreviousPlayer1;
    public Player PreviousPlayer2;

    // The constructor
    public Players() {

    }

    public void addPlayer(Player player) {
        PlayersArray.add(player);
    }

    public void RemoveAllPlayers() {
        PlayersArray.clear();
    }

    // public void SortArrayOnHighScore(){
    //}

    // Public void fillFromFile(){
    //}

    // Public void writeDataToFile(){
    //}








}
