package com.example.alex.ghostapp4;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Alex van der Meer
 * Student number: 10400958
 * on 12-10-2015.
 *
 * This class holds functions to read in a dictionary from a file, it is placed in a Hashset
 * The two filter functions could be made shorter by using the .startswith function of the string class
 * however a faster implementation is chosen.
 * The set is repeadedly filtered per letter to reduce the amount of comperisons
 */
public class Lexicon {

    private HashSet<String> FullDictionary = new HashSet<>();
    public HashSet<String> CurrentFilteredSet = new HashSet<>();
    public int DefaultPrefixSize = 3;
    public int LetterIndex = DefaultPrefixSize; // The LetterIndex is used in the FilterLetter function and is used from the third letter onwards

    public Lexicon(Context context, String language){
        String FileName;
        if (language.equals("dutch"))
            FileName = "dutch.txt";
        // Language is english
        else{
            FileName = "english.txt";
        }
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

    // This function filters on the 3 letter prefix by fist filtering the entire dictionary
    // for the first letter, then the resulting set by the second letter, and so on. This reduces the total amount of comperasons
    public void FilterOnInitialPrefix(String Prefix) {
        HashSet<String> SubSetLetter1 = new HashSet<>();
        HashSet<String> SubSetLetter2 = new HashSet<>();
        HashSet<String> SubSetLetter3 = new HashSet<>();

        // Only the first loop uses the FullDictionary
        Iterator IteratorOnSet0 = FullDictionary.iterator();
        char FilterLetter1 = Prefix.charAt(0);
        while (IteratorOnSet0.hasNext()) {
            String Word = IteratorOnSet0.next().toString();
            if (Word.charAt(0) == FilterLetter1) {
                SubSetLetter1.add(Word);
            }
        }
        Iterator IteratorOnSet1 = SubSetLetter1.iterator();
        char FilterLetter2 = Prefix.charAt(1);
        while (IteratorOnSet1.hasNext()) {
            String Word = IteratorOnSet1.next().toString();
            if (Word.length() > 1 ){
                if (Word.charAt(1) == FilterLetter2) {
                    SubSetLetter2.add(Word);
                }
            }
        }
        Iterator IteratorOnSet2 = SubSetLetter2.iterator();
        char FilterLetter3 = Prefix.charAt(2);
        while (IteratorOnSet2.hasNext()) {
            String Word = IteratorOnSet2.next().toString();
            if (Word.length() > 2 ){
                if (Word.charAt(2) == FilterLetter3) {
                    SubSetLetter3.add(Word);
                }
            }
        }
        CurrentFilteredSet = SubSetLetter3;
    }

    // This function filters the CurrentSet for the next letter, making it allot smaller
    public void FilterOnLetter(char Letter){
        HashSet<String> TempResultSet = new HashSet<>();
        Iterator IteratorOnSet = CurrentFilteredSet.iterator();
        while (IteratorOnSet.hasNext()) {
            String Word = IteratorOnSet.next().toString();
            if (Word.length() > LetterIndex ){
                if (Word.charAt(LetterIndex) == Letter) {
                    TempResultSet.add(Word);
                }
            }
        }
        CurrentFilteredSet = TempResultSet;
        // Next loop the letter will be one index further
        LetterIndex++;
    }

    public int count(){
        return CurrentFilteredSet.size();
    }

    public void resetClass(){
        LetterIndex = DefaultPrefixSize;
        CurrentFilteredSet.clear();
    }
}
