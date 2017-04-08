package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.configuration.LanguageHelper;

/**
 * Settings activity used to change settings (e.g. language)
 */
public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /* Session variables */
    private String user_name;
    private String user_id;

    // Spinner to choose language
    private Spinner spinnerLanguage;
    // Button to  update
    private Button buttonSaveConfig;

    /**
     * On create function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        setTitle(R.string.title_activity_settings);

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
        spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        buttonSaveConfig = (Button) findViewById(R.id.buttonSaveConfig);
        // Update language on click
        buttonSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((String) spinnerLanguage.getSelectedItem()) {
                    case "Francais":
                        MyGlobalVars.TAG_CURRENT_LANGUAGE = "fr";
                        break;
                    default:
                        MyGlobalVars.TAG_CURRENT_LANGUAGE = "en";
                }
                updateLanguage();
            }
        });

    }

    /**
     * Refresh activity on language change
     */
    private void updateLanguage()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra(MyGlobalVars.TAG_ID, user_id);
        i.putExtra(MyGlobalVars.TAG_NAME, user_name);
        startActivity(i);
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
