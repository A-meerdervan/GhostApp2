package com.example.alex.ghostapp2;

/**
 * Created by Alex on 7-10-2015.
 */
public class Player {

    private String Name = "unitialized";
    private int Score = 0;

    public Player(String name, int score){
        Name = name;
        Score = score;
    }

    // If the player wins a game
    public void increaseScore(){
        Score += 10;
    }

    // if the player loses a game
    public void decreaseScore(){
        if (Score > 4){
            Score -= 5;
        }
    }
}
