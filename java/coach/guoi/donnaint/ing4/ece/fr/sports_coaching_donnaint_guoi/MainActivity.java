package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        addMatchToView(fragmentTransaction,"match2");
        addMatchToView(fragmentTransaction,"match3");
        addMatchToView(fragmentTransaction,"match4");
        fragmentTransaction.commit();
    }

    public void addMatchToView(FragmentTransaction fragmentTransaction, String id) {
        GameInfoFragment gameInfo = new GameInfoFragment();
        gameInfo.setArguments(createGameBundle("Nicolas","Kevin","Donnaint","Guoi",id));
        fragmentTransaction.add(R.id.mainGameContainer, gameInfo);
    }

    public Bundle createGameBundle (String player1, String player2, String team1, String team2,
                                    String score) {
        Bundle gameBundle = new Bundle();
        gameBundle.putString(MyGlobalVars.TAG_PLAYER1, player1);
        gameBundle.putString(MyGlobalVars.TAG_PLAYER2, player2);
        gameBundle.putString(MyGlobalVars.TAG_TEAM1, team1);
        gameBundle.putString(MyGlobalVars.TAG_TEAM2, team2);
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

        if (id == R.id.nav_starcraft) {
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
}
