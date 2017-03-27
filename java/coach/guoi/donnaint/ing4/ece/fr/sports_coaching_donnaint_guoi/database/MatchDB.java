package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Kevin on 27/03/2017.
 */

public class MatchDB {
    private static final int VERSION_BDD = 1;
    private static final String MATCH_DB = "match.db";

    private static final String TABLE_MATCH = "table_match";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_SCORE = "Score";
    private static final int NUM_COL_SCORE = 1;
    private static final String COL_TYPE = "Type";
    private static final int NUM_COL_TYPE = 2;
    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 3;
    private static final String COL_TEAM1 = "Team1";
    private static final int NUM_COL_TEAM1 = 4;
    private static final String COL_TEAM2 = "Team2";
    private static final int NUM_COL_TEAM2 = 5;

    private SQLiteDatabase matchDb;
    private MatchSQLite matchSQLite;

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
     * Get database
     * @return
     */
    public SQLiteDatabase getMatchDb() {
        return matchDb;
    }

    /**
     * Insert new match
     * @param match
     * @return
     */
    public long insertMatch(Match match){
        // Create ContentValues (like HashMap)
        ContentValues values = new ContentValues();
        // Link key to value
        values.put(COL_SCORE, match.getScore());
        values.put(COL_TYPE, match.getType());
        values.put(COL_DATE, match.getDate());
        values.put(COL_TEAM1, match.getTeam1());
        values.put(COL_TEAM2, match.getTeam2());
        // Add to database
        return matchDb.insert(TABLE_MATCH, null, values);
    }


    /**
     * Find match by player
     * @return
     */
    public ArrayList<Match> getAllMatches(){
        String queryString = "SELECT * FROM " + TABLE_MATCH;
        Cursor c = matchDb.rawQuery(queryString, new String[]{});
        // If nothing is found, return null
        if (c.getCount() == 0)
            return null;

        ArrayList<Match> matches = new ArrayList<>();
        // Else go to first element
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            // Create a match corresponding to the return value of the query
            Match match = new Match(c.getInt(NUM_COL_ID), c.getString(NUM_COL_SCORE),
                    c.getString(NUM_COL_TYPE), c.getString(NUM_COL_DATE),
                    c.getString(NUM_COL_TEAM1), c.getString(NUM_COL_TEAM2));
            matches.add(match);
        }
        // Close cursor
        c.close();

        // Return match
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
        // Link key to value
        values.put(COL_SCORE, match.getScore());
        values.put(COL_TYPE, match.getType());
        values.put(COL_DATE, match.getDate());
        values.put(COL_TEAM1, match.getTeam1());
        values.put(COL_TEAM2, match.getTeam2());
        // Add to database
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
                c.getString(NUM_COL_TEAM1), c.getString(NUM_COL_TEAM2));
        // Close cursor
        c.close();

        // Return match
        return match;
    }

    private void deleteDb() {
    }
}
