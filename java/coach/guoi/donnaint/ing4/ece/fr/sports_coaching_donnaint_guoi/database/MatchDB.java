package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MatchDB class equivalent to creating match table in database
 * Created by Kevin on 27/03/2017.
 */

public class MatchDB {
    // Database version
    private static final int VERSION_BDD = 1;
    // Database name
    private static final String MATCH_DB = "matchs.db";
    // Table name
    private static final String TABLE_MATCH = "table_match";
    // ID column name and number
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    // Score column name and number
    private static final String COL_SCORE = "Score";
    private static final int NUM_COL_SCORE = 1;
    // Type column name and number
    private static final String COL_TYPE = "Type";
    private static final int NUM_COL_TYPE = 2;
    // Date column name and number
    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 3;
    // Team1 column name and number
    private static final String COL_TEAM1 = "Team1";
    private static final int NUM_COL_TEAM1 = 4;
    // Team2 column name and number
    private static final String COL_TEAM2 = "Team2";
    private static final int NUM_COL_TEAM2 = 5;
    // Latitude column name and number
    private static final String COL_LATITUDE = "Latitude";
    private static final int NUM_COL_LATITUDE = 6;
    // Longitude column name and number
    private static final String COL_LONGITUDE = "Longitude";
    private static final int NUM_COL_LONGITUDE = 7;

    // SQLite database
    private MatchSQLite matchSQLite;
    // SQLite writable database
    private SQLiteDatabase matchDb;

    /**
     * Constructor calling super constructor
     * @param context
     */
    public MatchDB(Context context) {
        this.matchSQLite = new MatchSQLite(context, MATCH_DB, null, VERSION_BDD);
    }

    /**
     * Open database
     */
    public void open(){
        // Open db in writing
        matchDb = matchSQLite.getWritableDatabase();
    }

    /**
     * close database
     */
    public void close(){
        // Open db in writing
        matchDb.close();
    }

    /**
     * Insert new match
     * @param match
     * @return
     */
    public int insertMatch(Match match){
        // Create ContentValues (like HashMap)
        ContentValues values = new ContentValues();
        // Link column name to value
        values.put(COL_SCORE, match.getScore());
        values.put(COL_TYPE, match.getType());
        values.put(COL_DATE, match.getDate());
        values.put(COL_TEAM1, match.getTeam1());
        values.put(COL_TEAM2, match.getTeam2());
        values.put(COL_LATITUDE, match.getLatitude());
        values.put(COL_LONGITUDE, match.getLongitude());
        // Add to database and return match id
        return (int) matchDb.insert(TABLE_MATCH, null, values);
    }


    /**
     * Get all matches in database
     * @return
     */
    public ArrayList<Match> getAllMatches(){
        ArrayList<Match> matches = new ArrayList<>();
        String queryString = "SELECT" + COL_ID + COL_SCORE + COL_TYPE +
                COL_DATE + COL_TEAM1 + COL_TEAM2 + COL_LATITUDE + COL_LONGITUDE
                + "FROM " + TABLE_MATCH;
        Cursor c = matchDb.query(TABLE_MATCH, new String[]{COL_ID, COL_SCORE, COL_TYPE,
                COL_DATE, COL_TEAM1, COL_TEAM2, COL_LATITUDE, COL_LONGITUDE},null, null, null, null, null);
        // If nothing is found, return empty arrayList
        if (c.getCount() == 0)
            return matches;

        // Else go to first element
        c.moveToFirst();
        do {
            // Create a match corresponding to the return value of the query
            Match match = new Match(c.getInt(NUM_COL_ID), c.getString(NUM_COL_SCORE),
                    c.getString(NUM_COL_TYPE), c.getString(NUM_COL_DATE),
                    c.getString(NUM_COL_TEAM1), c.getString(NUM_COL_TEAM2),
                    c.getString(NUM_COL_LATITUDE), c.getString(NUM_COL_LONGITUDE));
            matches.add(match);
        } while (c.moveToNext());

        // Close cursor
        c.close();

        // Return list of match
        return matches;
    }

    /**
     * Update match by id
     * @param id
     * @param match
     * @return
     */
    public long updateMatchById(int id, Match match){
        // Create ContentValues (like HashMap)
        ContentValues values = new ContentValues();
        // Link column name to value
        values.put(COL_SCORE, match.getScore());
        values.put(COL_TYPE, match.getType());
        values.put(COL_DATE, match.getDate());
        values.put(COL_TEAM1, match.getTeam1());
        values.put(COL_TEAM2, match.getTeam2());
        values.put(COL_LATITUDE, match.getLatitude());
        values.put(COL_LONGITUDE, match.getLongitude());
        // Update in database by ID
        return matchDb.update(TABLE_MATCH, values, COL_ID + " = " +id, null);
    }

    /**
     * Remove match from id
     * @param id
     * @return
     */
    public int removeMatchById(int id){
        // Remove match by ID
        return matchDb.delete(TABLE_MATCH, COL_ID + " = " +id, null);
    }

    /**
     * Find match by player
     * @param name
     * @return
     */
    public Match getMatchByPlayer(String name){
        String queryString = "SELECT * FROM " + TABLE_MATCH
                + " WHERE " + COL_TEAM1 + " LIKE ? OR " + COL_TEAM2 + " LIKE ?";
        String[] nameArray = new String[] {name};
        Cursor c = matchDb.rawQuery(queryString, nameArray);
        return cursorToMatch(c);
    }

    /**
     * Convert cursor to match
     * @param c
     * @return
     */
    private Match cursorToMatch(Cursor c){
        // If nothing is found, return null
        if (c.getCount() == 0)
            return null;
        // Else go to first element
        c.moveToFirst();
        // Create a match corresponding to the return value of the query
        Match match = new Match(c.getInt(NUM_COL_ID), c.getString(NUM_COL_SCORE),
                c.getString(NUM_COL_TYPE), c.getString(NUM_COL_DATE),
                c.getString(NUM_COL_TEAM1), c.getString(NUM_COL_TEAM2),
                c.getString(NUM_COL_LATITUDE), c.getString(NUM_COL_LONGITUDE));
        // Close cursor
        c.close();
        // Return match
        return match;
    }

    /**
     * Empty table match in database
     */
    public void clearDatabase() {
        String clearDBQuery = "DELETE FROM "+TABLE_MATCH;
        matchDb.execSQL(clearDBQuery);
    }
}
