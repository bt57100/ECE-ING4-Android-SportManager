package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

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
import android.widget.TextView;

/**
 * Starcraft activity display Starcraft's games
 */
public class StarcraftActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /* Session variables */
    private String user_name;
    private String user_id;

    /**
     * On create function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starcraft);

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
}
