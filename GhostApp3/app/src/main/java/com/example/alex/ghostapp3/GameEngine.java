package com.example.alex.ghostapp3;

/**
 * Created by Alex on 5-10-2015.
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
        } else{
            switch (Reason){
                case 1:
                    ReasonForWinning = "The other user made a prefix with which, no words start.";
                    break;
                case 2:
                    ReasonForWinning = "The other user guessed an existing word";
                    break;
            }
        }
    }

    public void resetGame(){
        FirstGuessNotMadeYet = true;
        Prefix = "";
        lexicon.resetClass();
    }
}
