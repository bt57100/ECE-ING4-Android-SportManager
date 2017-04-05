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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Iview,
        OnMapReadyCallback {

    public MatchDB matchDB = new MatchDB(this);
    public TextView textAddMatch;
    public GridLayout gridAddMatch;
    public EditText editAddTeam1;
    public EditText editAddTeam2;
    public EditText editAddDate;
    public EditText editAddType;
    public EditText editAddScore;
    public Button buttonAddMatch;

    private Marker marker;
    private GoogleMap googleMap = null;
    private LatLng latlng = new LatLng(48,2);
    private HashMap<Integer, LatLng> latLngMap = new HashMap<>();
    private HashMap<Integer, Marker> markerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(R.string.title_activity_news);
        textAddMatch = (TextView) findViewById(R.id.textAddMatch);
        gridAddMatch = (GridLayout) findViewById(R.id.gridAddMatch);
        editAddTeam1 = (EditText) findViewById(R.id.editAddTeam1);
        editAddTeam2 = (EditText) findViewById(R.id.editAddTeam2);
        editAddDate = (EditText) findViewById(R.id.editAddDate);
        editAddType = (EditText) findViewById(R.id.editAddType);
        editAddScore = (EditText) findViewById(R.id.editAddScore);
        buttonAddMatch = (Button) findViewById(R.id.buttonAddMatch);
        textAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridAddMatch.setVisibility(View.VISIBLE);
                textAddMatch.setVisibility(View.GONE);
            }
        });
        buttonAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatchValid();
            }
        });

        if(savedInstanceState == null) {
            matchDB.open();
            ArrayList<Match> matches = matchDB.getAllMatches();
            matchDB.close();
            // If database empty add one for instance
            if (matches.isEmpty()) {
                matchDB.open();
                Match match = new Match("2 - 1", "BO3", "2017/03/17", "Guoi", "Donnaint", "48.8584", "2.2945");
                matchDB.insertMatch(match);
                matches = matchDB.getAllMatches();
                matchDB.close();
            }
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

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(MyGlobalVars.TAG_CURRENT_LANGUAGE);
        Context context = LanguageHelper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

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

    public void addMatchValid() {
        boolean cancel = false;
        View focusView = null;
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
            // form field with an error.
            focusView.requestFocus();
        } else {
            Match newMatch = new Match(editAddScore.getText().toString(),
                    editAddType.getText().toString(),
                    editAddDate.getText().toString(),
                    editAddTeam1.getText().toString(),
                    editAddTeam2.getText().toString(),
                    String.valueOf(latlng.latitude),
                    String.valueOf(latlng.longitude));
            matchDB.open();
            newMatch.setId(matchDB.insertMatch(newMatch));
            matchDB.close();
            FragmentTransaction fragmentTransaction = addMatchToView(newMatch.getId(),
                    newMatch.getTeam1(), newMatch.getTeam2(),
                    newMatch.getScore(), newMatch.getDate(), newMatch.getType());
            fragmentTransaction.commit();
            limitSavedMatches();
            addMaps(newMatch.getId());
            gridAddMatch.setVisibility(View.GONE);
            textAddMatch.setVisibility(View.VISIBLE);
        }
    }

    public void limitSavedMatches() {
        matchDB.open();
        ArrayList<Match> matches = matchDB.getAllMatches();
        matchDB.close();
        while (matches.size() > MyGlobalVars.NB_SAVED_MATCHES) {
            String tag = String.valueOf(matches.get(0).getId());
            Fragment fragment = getFragmentManager().findFragmentByTag(tag);
            if(fragment instanceof GameInfoFragment) {
                removeFragment((GameInfoFragment) fragment);
                matchDB.open();
                matches = matchDB.getAllMatches();
                matchDB.close();
            } else {
                Toast.makeText(this, "Failed to reduced", Toast.LENGTH_LONG);
                break;
            }
        }
    }

    private boolean isNameValid(String name) {
        if(name.length() > 30) {
            return false;
        } else {
            return true;
        }
    }

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_last_updates) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_starcraft) {
            Intent i = new Intent(this, StarcraftActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_chat) {
            Intent i = new Intent(this, ChatActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        final GoogleMap map = this.googleMap;
        LatLng position = new LatLng(48.8584, 2.2945);
        this.marker = map.addMarker(new MarkerOptions().position(position).title("Current Position"));
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.remove();
                marker = map.addMarker(new MarkerOptions().position(latLng).title("Current Position"));
                latlng = latLng;
            }
        });
        for (Integer key : latLngMap.keySet()) {
            markerMap.put(key, this.googleMap.addMarker(new MarkerOptions().position(latLngMap.get(key))));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngMap.get(key)));
        }
    }

    public void addMaps(int matchId) {
        latLngMap.put(matchId,latlng);
        markerMap.put(matchId, this.googleMap.addMarker(new MarkerOptions().position(latlng)));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }
}
