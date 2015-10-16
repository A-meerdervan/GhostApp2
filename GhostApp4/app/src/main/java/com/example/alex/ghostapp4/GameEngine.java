package com.example.alex.ghostapp4;

/**
 * Created by Alex on 5-10-2015.
 *
 * This class is responsible for applying the game's rules
 * The most important function receives the user's guess and returns wheter the game ended
 *
 * The class uses a Lexicon object to hold and filter the dictionary
 */
public class GameEngine {

    public GameEngine(Lexicon lexiCon, String language){
        lexicon = lexiCon;
        Language = language;
    }

    public GameEngine(Lexicon lexiconn, String language, boolean firstGuessNotMadeYet){
        FirstGuessNotMadeYet = firstGuessNotMadeYet;
        lexicon = lexiconn;
        Language = language;
    }

    private String Language = "uninitialized";
    public Lexicon lexicon;
    public String Prefix = "";
    public boolean FirstGuessNotMadeYet = true;
    public String ReasonForWinning = "Uninitialised";

    // This function tests whether the game was lost and states the reason for winning
    public boolean gameEndedAfterGuess(String Guess){
        Boolean PlayerLost = true;
        Boolean GameContinues = false;
        // The first guess is 3 letters long
        if (FirstGuessNotMadeYet){
            Prefix = Guess;
            lexicon.FilterOnInitialPrefix(Prefix);
            // If The user made a prefix, with wich, no words start, the user loses (A verry short game)
            if (lexicon.count() == 0){
                setReasonForWinning(1);
                return PlayerLost;
            }
            // the game did not end
            FirstGuessNotMadeYet = false;
            return GameContinues;
        }
        // When the guess is after the first 3 letter guess, it is 1 letter.
        // If the user guessed an existing word, the user loses
        if(lexicon.CurrentFilteredSet.contains(Prefix + Guess)){
            setReasonForWinning(2);
            return PlayerLost;
        }
        // Check after applying the filter
        Prefix = Prefix + Guess;
        lexicon.FilterOnLetter(Guess.charAt(0));
        // The user made a prefix, with wich, no words start.
        if (lexicon.count() == 0) {
            setReasonForWinning(1);
            return PlayerLost;
        }
        // the game did not end
        return GameContinues;
    }

    // This function returns the reason for winning depeding on where it was called
    // in the previous function, it checks the language
    public void setReasonForWinning(int Reason) {
        if (Language.equals("dutch")){
            switch (Reason){
                case 1:
                    ReasonForWinning = "Er zijn geen woorden die beginnen met het woordfragment dat de andere speler maakte";
                    break;
                case 2:
                    ReasonForWinning = "De andere speler maakte een bestaand woord";
                    break;
            }
            return;
        }
        // The Language was english
        switch (Reason){
            case 1:
                ReasonForWinning = "The other user made a prefix with which, no words start.";
                break;
            case 2:
                ReasonForWinning = "The other user guessed an existing word";
                break;
        }
    }

    public void resetGame(){
        FirstGuessNotMadeYet = true;
        Prefix = "";
        lexicon.resetClass();
    }
}
