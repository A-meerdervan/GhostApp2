package com.example.alex.ghostapp2;

/**
 * Created by Alex on 7-10-2015.
 */
public class Player implements java.io.Serializable {

    public String Name = "unitialized";
    public int Score = 0;

    // The 3 constructors
    public Player(String name){
        Name = name;
    }

    public Player(String name, int score){
        Name = name;
        Score = score;
    }
    public Player(){
    }

    // If the player wins a game
    public void increaseScore(){
        Score = Score + 10;
    }

    // if the player loses a game
    public void decreaseScore(){
        if (Score > 4){
            Score = Score - 5;
        }
    }
}
