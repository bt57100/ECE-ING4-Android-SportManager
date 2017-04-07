package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database;

import java.util.ArrayList;

/**
 * Match class is equivalent as a row in our local database containing 3 matchs
 * Created by Kevin on 27/03/2017.
 */

public class Match {
    // ID for database
    private int id;
    // Score of the match
    private String score;
    //Type of the match (e.g. Best of 3)
    private String type;
    // Date of the match
    private String date;
    // Teams playing
    private String team1;
    private String team2;
    // Position as set of latitude and longitude
    private String latitude;
    private String longitude;

    /**
     * Constructor with ID
     * @param id
     * @param score
     * @param type
     * @param date
     * @param team1
     * @param team2
     * @param latitude
     * @param longitude
     */
    public Match(int id, String score, String type, String date, String team1, String team2, String latitude, String longitude) {
        this.id = id;
        this.score = score;
        this.type = type;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructor without ID
     * @param score
     * @param type
     * @param date
     * @param team1
     * @param team2
     * @param latitude
     * @param longitude
     */
    public Match(String score, String type, String date, String team1, String team2, String latitude, String longitude) {
        this.score = score;
        this.type = type;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Setter for ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for Score
     * @param score
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Setter for Type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for Date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for Score
     * @return
     */
    public String getScore() {
        return score;
    }

    /**
     * Getter for Type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for Date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for Team1
     * @return
     */
    public String getTeam1() {
        return team1;
    }

    /**
     * Getter for Team2
     * @return
     */
    public String getTeam2() {
        return team2;
    }

    /**
     * Getter for Latitude
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter for Longitude
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Convert match to a String
     * as Team1 score Team2
     * @return
     */
    public String toString() {
        return team1 + " " + score + " " + team2;
    }
}
