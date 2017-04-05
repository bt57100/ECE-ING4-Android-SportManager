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

import java.util.Locale;

import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.configuration.LanguageHelper;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinnerLanguage;
    private Button buttonSaveConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(R.string.title_activity_settings);
        spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        buttonSaveConfig = (Button) findViewById(R.id.buttonSaveConfig);
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

    private void updateLanguage()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(MyGlobalVars.TAG_CURRENT_LANGUAGE);
        Context context = LanguageHelper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
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
}
