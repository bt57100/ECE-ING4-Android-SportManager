package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Kevin on 27/03/2017.
 */

public class MatchSQLite extends SQLiteOpenHelper {

    private static final String TABLE_MATCH = "table_match";

    private static final String COL_ID = "ID";
    private static final String COL_SCORE = "Score";
    private static final String COL_TYPE = "Type";
    private static final String COL_DATE = "Date";
    private static final String COL_TEAM1 = "Team1";
    private static final String COL_TEAM2 = "Team2";
    private static final String COL_LATITUDE = "Latitude";
    private static final String COL_LONGITUDE = "Longitude";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_MATCH + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SCORE + " TEXT NOT NULL, "
            + COL_TYPE + " TEXT NOT NULL, " + COL_DATE + " TEXT NOT NULL, "
            + COL_TEAM1 + " TEXT NOT NULL, " + COL_TEAM2 + " TEXT NOT NULL, "
            + COL_LATITUDE + " TEXT NOT NULL, " + COL_LONGITUDE + " TEXT NOT NULL);";


    public MatchSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_MATCH + ";");
        onCreate(db);
    }
}
