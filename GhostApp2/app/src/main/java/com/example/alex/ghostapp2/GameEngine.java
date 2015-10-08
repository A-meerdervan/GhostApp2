package com.example.alex.ghostapp2;

import android.util.Log;

/**
 * Created by Alex on 5-10-2015.
 */
public class GameEngine {

    public GameEngine(Lexicon lexiconn, String language){
        lexicon = lexiconn;
        Language = language;
    }

    public GameEngine(Lexicon lexiconn, String language, boolean firstGuessNotMadeYet){
        FirstGuessNotMadeYet = firstGuessNotMadeYet;
        lexicon = lexiconn;
        Language = language;
    }

    private String Language = "uninitialized";
    public Lexicon lexicon;
    // Wellicht is de regel hieronder nooit nodig
    public String Prefix = "";
    public boolean FirstGuessNotMadeYet = true;
    public String ReasonForWinning = "Uninitialised";

    public boolean gameEndedAfterGuess(String Guess){
        // The first guess is 3 letters long
        if (FirstGuessNotMadeYet){
            Prefix = Guess;
            lexicon.FilterOnInitialPrefix(Prefix);
            // If The user made a prefix, with wich, no words start, the user loses (A verry short game)
            if (lexicon.count() == 0){
                currentUserLost();
                ReasonForWinning = "The other user made a prefix with which, no words start.";
                return true;
            }
            // the game did not end
            else {
                FirstGuessNotMadeYet = false;
                return false;
            }

        }
        // When the guess is after the first 3 letter guess, it is 1 letter.
        else{
            // If the user guessed an existing word, the user loses
            if(lexicon.CurrentFilteredSet.contains(Prefix + Guess)){
                currentUserLost();
                ReasonForWinning = "The other user guessed an existing word";
                return true;
            }
            else{
                Prefix = Prefix + Guess;
                // Dit moet nog mooi, weet nog niet hoe
                lexicon.FilterOnLetter(Guess.charAt(0));
                // The user made a prefix, with wich, no words start.
                if (lexicon.count() == 0){
                    currentUserLost();
                    ReasonForWinning = "The other user made a prefix with which, no words start.";
                    return true;
                }
                // the game did not end
                else {
                    return false;
                }
            }
        }
    }


    public void currentUserLost(){
        Log.d("xzzr", "Je hebt verloren man");
        // Nog geen idee
        // Sowieso de activity laten weten dat ie moet eindigen enzo
    }

    public void resetGame(){
        // what to do?
        // reset lexicon
        FirstGuessNotMadeYet = true;
        Prefix = "";
        lexicon.resetClass();
    }

}
