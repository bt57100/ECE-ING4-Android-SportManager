package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.configuration.LanguageHelper;
import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database.Match;
import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database.MatchDB;

/**
 * Main activity
 * display match in local database on fragment and google map
 * enable add match to local database (click on map then add match info)
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Iview,
        OnMapReadyCallback {
    /* Session variables */
    private String user_name = "Username";
    private String user_id = "0";

    // Match database
    private MatchDB matchDB = new MatchDB(this);
    // Text view to add a match
    private TextView textAddMatch;
    // Grid Layout to add a match
    private GridLayout gridAddMatch;
    /* EditText to add data to new match */
    private EditText editAddTeam1;
    private EditText editAddTeam2;
    private EditText editAddDate;
    private EditText editAddType;
    private EditText editAddScore;
    // Button to add match to local database and display
    private Button buttonAddMatch;

    // Current marker position (position store if a match is added)
    private Marker marker;
    // Google map fragment
    private GoogleMap googleMap = null;
    // Position of the marker (initialize in Paris)
    private LatLng latlng = new LatLng(48,2);
    /* Maps containing position and marker with matc id as key */
    private HashMap<Integer, LatLng> latLngMap = new HashMap<>();
    private HashMap<Integer, Marker> markerMap = new HashMap<>();

    /**
     * On create function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Navigation drawer initialization */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set activity title
        setTitle(R.string.title_activity_news);

        /* Session variables */
        if(getIntent().hasExtra(MyGlobalVars.TAG_ID)){
            this.user_id = getIntent().getStringExtra(MyGlobalVars.TAG_ID);
        }
        if(getIntent().hasExtra(MyGlobalVars.TAG_NAME)){
            this.user_name = getIntent().getStringExtra(MyGlobalVars.TAG_NAME);
            View header = navigationView.getHeaderView(0);
            TextView textUsername = (TextView) header.findViewById(R.id.textUsername);
            textUsername.setText(user_name);
        }


        // Initialize all views
        textAddMatch = (TextView) findViewById(R.id.textAddMatch);
        gridAddMatch = (GridLayout) findViewById(R.id.gridAddMatch);
        editAddTeam1 = (EditText) findViewById(R.id.editAddTeam1);
        editAddTeam2 = (EditText) findViewById(R.id.editAddTeam2);
        editAddDate = (EditText) findViewById(R.id.editAddDate);
        editAddType = (EditText) findViewById(R.id.editAddType);
        editAddScore = (EditText) findViewById(R.id.editAddScore);
        buttonAddMatch = (Button) findViewById(R.id.buttonAddMatch);
        // On click display grid to add match
        textAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridAddMatch.setVisibility(View.VISIBLE);
                textAddMatch.setVisibility(View.GONE);
            }
        });
        // Add match to local database
        buttonAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatchValid();
            }
        });

        if(savedInstanceState == null) {
            // Get all match
            matchDB.open();
            ArrayList<Match> matches = matchDB.getAllMatches();
            matchDB.close();
            // If database empty add one as example
            if (matches.isEmpty()) {
                matchDB.open();
                Match match = new Match("2 - 1", "BO3", "2017/03/17", "Guoi", "Donnaint", "48.8584", "2.2945");
                matchDB.insertMatch(match);
                matches = matchDB.getAllMatches();
                matchDB.close();
            }
            // Add fragment to layout to display each mach stored
            for (Match match : matches) {
                FragmentTransaction fragmentTransaction = addMatchToView(match.getId(),
                        match.getTeam1(), match.getTeam2(),
                        match.getScore(), match.getDate(), match.getType());
                fragmentTransaction.commit();
                LatLng latLng = new LatLng(Double.parseDouble(match.getLatitude()), Double.parseDouble(match.getLongitude()));
                latLngMap.put(match.getId(), latLng);
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    /**
     * Set base context to activity
     * enable language change
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(MyGlobalVars.TAG_CURRENT_LANGUAGE);
        Context context = LanguageHelper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    /**
     * Get a fragment containing match information
     * @param id
     * @param team1
     * @param team2
     * @param score
     * @param date
     * @param type
     * @return
     */
    public FragmentTransaction addMatchToView(int id, String team1, String team2,
                                              String score, String date, String type) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        GameInfoFragment gameInfo = new GameInfoFragment();
        gameInfo.setArguments(createGameBundle(id,team1,team2,score, date, type));
        gameInfo.setMainView(this);
        String tag = String.valueOf(id);
        fragmentTransaction.add(R.id.mainGameContainer, gameInfo, tag);
        return fragmentTransaction;
    }

    /**
     * Check whether the entries are valid
     * If everything is valid add match to view
     */
    public void addMatchValid() {
        boolean cancel = false;
        View focusView = null;
        /* Check entries validity */
        if (TextUtils.isEmpty(editAddScore.getText())) {
            editAddScore.setError(getString(R.string.error_field_required));
            focusView = editAddScore;
            cancel = true;
        } else if (!isNameValid(editAddScore.getText().toString())) {
            editAddScore.setError(getString(R.string.error_invalid_name_format));
            focusView = editAddScore;
            cancel = true;
        }
        if (TextUtils.isEmpty(editAddTeam1.getText())) {
            editAddTeam1.setError(getString(R.string.error_field_required));
            focusView = editAddTeam1;
            cancel = true;
        } else if (!isNameValid(editAddTeam1.getText().toString())) {
            editAddScore.setError(getString(R.string.error_invalid_name_format));
            focusView = editAddTeam1;
            cancel = true;
        }
        if (TextUtils.isEmpty(editAddTeam2.getText())) {
            editAddTeam2.setError(getString(R.string.error_field_required));
            focusView = editAddTeam2;
            cancel = true;
        } else if (!isNameValid(editAddTeam2.getText().toString())) {
            editAddTeam2.setError(getString(R.string.error_invalid_name_format));
            focusView = editAddTeam2;
            cancel = true;
        }
        if (TextUtils.isEmpty(editAddType.getText())) {
            editAddType.setError(getString(R.string.error_field_required));
            focusView = editAddType;
            cancel = true;
        } else if (!isNameValid(editAddType.getText().toString())) {
            editAddType.setError(getString(R.string.error_invalid_email));
            focusView = editAddType;
            cancel = true;
        }
        if (TextUtils.isEmpty(editAddDate.getText())) {
            editAddDate.setError(getString(R.string.error_field_required));
            focusView = editAddDate;
            cancel = true;
        } else if (!isDateValid(editAddDate.getText().toString())) {
            editAddDate.setError(getString(R.string.error_invalid_date_format));
            focusView = editAddDate;
            cancel = true;
        }
        if (cancel) {
            // There was an error
            // form field with an error focus on it
            focusView.requestFocus();
        } else {
            // Create a match with information
            Match newMatch = new Match(editAddScore.getText().toString(),
                    editAddType.getText().toString(),
                    editAddDate.getText().toString(),
                    editAddTeam1.getText().toString(),
                    editAddTeam2.getText().toString(),
                    String.valueOf(latlng.latitude),
                    String.valueOf(latlng.longitude));
            // Add to database
            matchDB.open();
            newMatch.setId(matchDB.insertMatch(newMatch));
            matchDB.close();
            // Add fragment of the match to view
            FragmentTransaction fragmentTransaction = addMatchToView(newMatch.getId(),
                    newMatch.getTeam1(), newMatch.getTeam2(),
                    newMatch.getScore(), newMatch.getDate(), newMatch.getType());
            fragmentTransaction.commit();
            // Limit match to MyGlobalVars.NB_SAVED_MATCHES (3)
            limitSavedMatches();
            addMaps(newMatch.getId());
            // Update visibility of the form
            gridAddMatch.setVisibility(View.GONE);
            textAddMatch.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Limit number of match save in database to MyGlobalVars.NB_SAVED_MATCHES (3)
     */
    public void limitSavedMatches() {
        // Get database matches
        matchDB.open();
        ArrayList<Match> matches = matchDB.getAllMatches();
        matchDB.close();
        while (matches.size() > MyGlobalVars.NB_SAVED_MATCHES) {
            String tag = String.valueOf(matches.get(0).getId());
            Fragment fragment = getFragmentManager().findFragmentByTag(tag);
            if(fragment instanceof GameInfoFragment) {
                // Remove fragment from view and from database
                removeFragment((GameInfoFragment) fragment);
                matchDB.open();
                matches = matchDB.getAllMatches();
                matchDB.close();
            } else {
                Toast.makeText(this, "Failed to reduced", Toast.LENGTH_SHORT);
                break;
            }
        }
    }

    /**
     * Check name validity (= 30 characters max)
     * @param name
     * @return
     */
    private boolean isNameValid(String name) {
        if(name.length() > 30) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check date format (YYYY-MM-dd) validity
     * @param birth
     * @return
     */
    private boolean isDateValid(String birth) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(birth);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    /**
     * Create bundle from match information to send to fragment
     * @param id
     * @param team1
     * @param team2
     * @param score
     * @param date
     * @param type
     * @return
     */
    public Bundle createGameBundle (int id, String team1, String team2, String score, String date, String type) {
        Bundle gameBundle = new Bundle();
        gameBundle.putInt(MyGlobalVars.TAG_ID, id);
        gameBundle.putString(MyGlobalVars.TAG_TEAM1, team1);
        gameBundle.putString(MyGlobalVars.TAG_TEAM2, team2);
        gameBundle.putString(MyGlobalVars.TAG_DATE, date);
        gameBundle.putString(MyGlobalVars.TAG_TYPE, type);
        gameBundle.putString(MyGlobalVars.TAG_SCORE, score);
        return gameBundle;
    }

    /**
     * Manage back press button
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Navigation drawer to navigate through menu
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_last_updates) {
            // Main page
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(MyGlobalVars.TAG_ID, user_id);
            i.putExtra(MyGlobalVars.TAG_NAME, user_name);
            startActivity(i);
        } else if (id == R.id.nav_starcraft) {
            // Starcraft page
            Intent i = new Intent(this, StarcraftActivity.class);
            i.putExtra(MyGlobalVars.TAG_ID, user_id);
            i.putExtra(MyGlobalVars.TAG_NAME, user_name);
            startActivity(i);
        } else if (id == R.id.nav_chat) {
            // Chat page
            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra(MyGlobalVars.TAG_ID, user_id);
            i.putExtra(MyGlobalVars.TAG_NAME, user_name);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            // Settings page
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra(MyGlobalVars.TAG_ID, user_id);
            i.putExtra(MyGlobalVars.TAG_NAME, user_name);
            startActivity(i);
        } else if (id == R.id.logout) {
            // Log out
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Remove fragment from view and match from database
     * @param fragment
     */
    @Override
    public void removeFragment(GameInfoFragment fragment) {
        matchDB.open();
        matchDB.removeMatchById(fragment.getMatchId());
        matchDB.close();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
        latLngMap.remove(fragment.getMatchId());
        markerMap.get(fragment.getMatchId()).remove();
        markerMap.remove(fragment.getMatchId());
    }

    /**
     * Move camera to selected match
     * @param fragment
     */
    public void moveToMarker(GameInfoFragment fragment) {
        LatLng latLng = markerMap.get(fragment.getMatchId()).getPosition();
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    /**
     * Move camera to selected match
     * @param fragment
     */
    @Override
    public void onSimpleClick(GameInfoFragment fragment) {
        moveToMarker(fragment);
    }

    /**
     * Manage google map view
     * @param googleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        /* Initialization */
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        final GoogleMap map = this.googleMap;
        // Default marker position
        LatLng position = new LatLng(48.8584, 2.2945);
        this.marker = map.addMarker(new MarkerOptions().position(position).title("Current Position"));
        // Listener to update current marker position
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.remove();
                marker = map.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
                latlng = latLng;
            }
        });
        // Add a marker for each match stored in database
        for (Integer key : latLngMap.keySet()) {
            markerMap.put(key, this.googleMap.addMarker(new MarkerOptions().position(latLngMap.get(key))));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngMap.get(key)));
        }
    }

    /**
     * Add a marker to google map using match ID
     * @param matchId
     */
    public void addMaps(int matchId) {
        latLngMap.put(matchId,latlng);
        markerMap.put(matchId, this.googleMap.addMarker(new MarkerOptions().position(latlng)));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }
}
