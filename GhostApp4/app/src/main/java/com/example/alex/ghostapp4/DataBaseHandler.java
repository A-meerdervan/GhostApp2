package com.example.alex.ghostapp4;

/**
 * Created by Alex on 15-10-2015.
 *
 * This class does operations on a database with all information on previous and current players
 * The database is query'd with SQL
 *
 * It holds simple functions to add, delete, find and read players
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper{

    // database version
    private static final int DATABASE_VERSION = 1;
    // database name
    private static final String DATABASE_NAME = "GhostDataBase";
    private static final String TABLE_PLAYERS = "Players";
    private static final String PLAYER_ID = "id";
    private static final String PLAYER_NAME = "Name";
    private static final String PLAYER_SCORE = "Score";

    private static final String[] COLUMNS = { PLAYER_ID, PLAYER_NAME, PLAYER_SCORE };

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create player table
        String Query = "CREATE TABLE " + TABLE_PLAYERS +  " ( " +
                PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLAYER_NAME + " TEXT, " +
                PLAYER_SCORE + " INTEGER )";
        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop books table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        this.onCreate(db);
    }

    public void createPlayer(Player player) {
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.Name);
        values.put(PLAYER_SCORE, player.Score);

        // insert Player
        db.insert(TABLE_PLAYERS, null, values);

        // close database transaction
        db.close();
    }

    public Player readPlayer(int id) {
        // get reference of the database
        SQLiteDatabase db = this.getReadableDatabase();

        // get player query
        Cursor cursor = db.query(TABLE_PLAYERS, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        Player player = new Player();
        player.Id = Integer.parseInt(cursor.getString(0));
        player.Name = cursor.getString(1);
        player.Score = Integer.parseInt(cursor.getString(2));

        return player;
    }

    public Player readPlayerByName(String Name) {
        // get reference of the database
        SQLiteDatabase db = this.getReadableDatabase();

        // get player query
        Cursor cursor = db.query(TABLE_PLAYERS, // a. table
                COLUMNS, " " + PLAYER_NAME + " = ?", new String[]{Name}, null, null, null, null);

        // if results !=null, parse the first one
        if ( cursor.moveToFirst() ) {
            // start activity a
            Player player = new Player();
            player.Id = Integer.parseInt(cursor.getString(0));
            player.Name = cursor.getString(1);
            player.Score = Integer.parseInt(cursor.getString(2));
            return player;
        } else {
            return null;
        }
    }

    public List getAllPlayers() {
        ArrayList<Player> PlayersArray = new ArrayList<>();

        // select book query
        String query = "SELECT  * FROM " + TABLE_PLAYERS;

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Player player = new Player();
        if (cursor.moveToFirst()) {
            do {
                player = new Player();
                player.Id = Integer.parseInt(cursor.getString(0));
                player.Name = cursor.getString(1);
                player.Score = Integer.parseInt(cursor.getString(2));

                // Add book to books
                PlayersArray.add(player);
            } while (cursor.moveToNext());
        }
        return PlayersArray;
    }

    public int updatePlayer(Player player) {

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.Name); // get Name
        values.put(PLAYER_SCORE, player.Score); // get Score

        // update
        int i = db.update(TABLE_PLAYERS, values, PLAYER_ID + " = ?", new String[] { String.valueOf(player.Id) });

        db.close();
        return i;
    }

    // Deleting single book
    public void deletePlayer(Player player) {
        // get reference of the database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete player
        db.delete(TABLE_PLAYERS, PLAYER_ID + " = ?", new String[] { String.valueOf(player.Id)});
        db.close();
    }

    public void deleteAllHistory(){
        // get reference of the database
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM " + TABLE_PLAYERS;
        db.execSQL(Query);
    }
}

