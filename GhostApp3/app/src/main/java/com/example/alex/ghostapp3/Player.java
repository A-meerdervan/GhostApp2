package com.example.alex.ghostapp3;

/**
 * Created by Alex on 7-10-2015.
 */
public class Player implements java.io.Serializable {

    public String Name = "player";
    public int Score = 0;
    public int Id = 0;

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

    // Override the equals and hashcode functions so the hashset functions know when a player object is already in the set or not.

    @Override
    public boolean equals(Object o) {
        Player player = (Player)o;

        if (player == null){
            return false;
        }
        // if the object has the same name, they are equal because this game does not allow double names.
        if (player.Name.equals(this.Name)){
            return true;
        }
        return false;
    }

    // this function gives a unique number for each player
    @Override
    public int hashCode() {
        return Name.hashCode();
    }
}
