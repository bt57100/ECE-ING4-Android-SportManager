package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

/**
 * MyGlobalVars class used to stored static URL, TAG used for json,...
 * Created by Kevin on 20/03/2017.
 */

public class MyGlobalVars {

    /* URL */
    public static final String url_connect_user = "http://myhost.com/database/user/connect_user.php?";
    public static final String url_register_user = "http://myhost.com/database/user/create_user.php?";

    /* Tag for distant database */
    public static final int TIMEOUT = 4000;
    public static final String TAG_EMAIL = "EMAIL";
    public static final String TAG_MESSAGE = "MESSAGE";
    public static final String TAG_USER_ID = "USER_ID";
    public static final String TAG_USER = "USER";
    public static final String TAG_NAME = "NAME";
    public static final String TAG_PASSWORD = "PASSWORD";
    public static final String TAG_SUCCESS = "SUCCESS";

    /* TAG for local database */
    public static final int NB_SAVED_MATCHES = 3;
    public static final String TAG_ID = "ID";
    public static final String TAG_TYPE = "TYPE";
    public static final String TAG_DATE = "DATE";
    public static final String TAG_TEAM1 = "TEAM1";
    public static final String TAG_TEAM2 = "TEAM2";
    public static final String TAG_SCORE = "SCORE";

    /* TAG current language */
    public static String TAG_CURRENT_LANGUAGE = "en";

    /* Number of picture taken each time */
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static String PATH = "";
}
