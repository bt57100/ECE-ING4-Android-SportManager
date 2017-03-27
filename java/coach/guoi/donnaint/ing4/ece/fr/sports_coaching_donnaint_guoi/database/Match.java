package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database;

import java.util.ArrayList;

/**
 * Created by Kevin on 27/03/2017.
 */

public class Match {
    private int id;
    private String score;
    private String type;
    private String date;
    private String team1;
    private String team2;

    public Match(int id, String score, String type, String date, String team1,
                 String team2) {
        this.id = id;
        this.score = score;
        this.type = type;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String toString() {
        return team1 + " " + score + " " + team2;
    }
}