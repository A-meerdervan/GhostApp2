package com.example.alex.ghostapp2;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Alex on 5-10-2015.
 */
public class Lexicon {

    private HashSet<String> FullDictionary = new HashSet<>();
    public HashSet<String> CurrentFilteredSet = new HashSet<>();
    public int LetterIndex = 3; // The LetterIndex is used in the FilterLetter function and is used from the third letter onwards

    public Lexicon(Context context, String language){
        String FileName;
        if (language.equals("dutch"))
            FileName = "dutch.txt";
        // Language is english
        else{
            FileName = "english.txt";
        }
        Log.d("xzzr", FileName);
        ReadFromfile(FileName, context);
    }

    public void ReadFromfile(String fileName, Context context) {
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            Log.d("xzzr","in try van readfromfile");
            fIn = context.getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            int i = 0;
            while ((line = input.readLine()) != null) {
                FullDictionary.add(line);
            }
            Log.d("xzzr", "Done reading file");
        } catch (Exception e) {
            Log.d("xzzr","kon helaas de file niet lezen/vinden");
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                Log.d("xzzr","kon de inpustream na het lezen van de file niet sluiten");
                e2.getMessage();
            }
        }
    }

    public void FilterOnInitialPrefix(String Prefix) {
        HashSet<String> SubSetLetter1 = new HashSet<>();
        HashSet<String> SubSetLetter2 = new HashSet<>();
        HashSet<String> SubSetLetter3 = new HashSet<>();

        // Alleen bij de eerste loop word de FullDictionary gebruikt.

        Iterator IteratorOnSet0 = FullDictionary.iterator();
        char FilterLetter1 = Prefix.charAt(0);
        while (IteratorOnSet0.hasNext()) {
            String Woord = IteratorOnSet0.next().toString();
            if (Woord.charAt(0) == FilterLetter1) {
                // Deze operatie, opzieken waar die hashset in de List zit, is die duur?
                SubSetLetter1.add(Woord);
            }
        }
        Iterator IteratorOnSet1 = SubSetLetter1.iterator();
        char FilterLetter2 = Prefix.charAt(1);
        while (IteratorOnSet1.hasNext()) {
            String Woord = IteratorOnSet1.next().toString();
            if (Woord.length() > 1 ){
                if (Woord.charAt(1) == FilterLetter2) {
                    SubSetLetter2.add(Woord);
                }
            }
        }
        Iterator IteratorOnSet2 = SubSetLetter2.iterator();
        char FilterLetter3 = Prefix.charAt(2);
        while (IteratorOnSet2.hasNext()) {
            String Woord = IteratorOnSet2.next().toString();
            if (Woord.length() > 2 ){
                if (Woord.charAt(2) == FilterLetter3) {
                    SubSetLetter3.add(Woord);
                    Log.d("xzzr",Woord);
                }
            }
        }

        // Return the subset with words starting with the prefix
        // Deze regel is niet nodig want je kan ook rechtstreeks CurrentFilteredSet gebruiken, wat is netter?
        CurrentFilteredSet = SubSetLetter3;
    }

    public void FilterOnLetter(char Letter){
        Log.d("xzzr", "LetterIndex: " + LetterIndex);
        HashSet<String> TempResultSet = new HashSet<>();
        Iterator IteratorOnSet = CurrentFilteredSet.iterator();
        while (IteratorOnSet.hasNext()) {
            String Word = IteratorOnSet.next().toString();
            // Maybe this check of word length is not needed, since the output of the previous filter set is checked for words with this length and end the game
            if (Word.length() > LetterIndex ){
                if (Word.charAt(LetterIndex) == Letter) {
                    TempResultSet.add(Word);
                }
            }
        }
        CurrentFilteredSet = TempResultSet;
        LetterIndex++;
    }

    public int count(){

        return CurrentFilteredSet.size();
    }

    public void resetClass(){
        LetterIndex = 3;
        CurrentFilteredSet.clear();
    }
}
