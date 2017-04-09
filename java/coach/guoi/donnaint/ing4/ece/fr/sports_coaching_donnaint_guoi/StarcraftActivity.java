package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.configuration.LanguageHelper;

/**
 * Starcraft activity to take photo for Starcraft's games
 */
public class StarcraftActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Iimage {
    /* Session variables */
    private String user_name;
    private String user_id;

    private Button buttonPictureStarcraft;

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

        /* Set button to take photo */
        buttonPictureStarcraft = (Button) findViewById(R.id.buttonPictureStarcraft);
        buttonPictureStarcraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        checkPermission();
    }

    private void checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
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
     * Open camera to take MyGlobalVars.REQUEST_IMAGE_CAPTURE (=1) picture
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, MyGlobalVars.REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Get the taken photo add a fragment to display it
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MyGlobalVars.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addImageToView(imageBitmap);
        }
    }

    /**
     * Remove this fragment from view
     */
    @Override
    public void removeFragment(ImageFragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    /**
     * Save image in gallery
     */
    @Override
    public void onSimpleClick(ImageFragment fragment) {
        MediaStore.Images.Media.insertImage(getContentResolver(), fragment.getImageBitmap(), fragment.getTitle(), "Starcraft II");
    }

    /**
     * Get a fragment containing new photo information
     * @return
     */
    public void addImageToView(Bitmap imageBitmap) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(createGameBundle(imageBitmap));
        imageFragment.setMainView(this);
        fragmentTransaction.add(R.id.imageContainer, imageFragment);
        fragmentTransaction.commit();
    }

    /**
     * Create bundle from match information to send to fragment
     * @param imageBitmap
     * @return
     */
    public Bundle createGameBundle (Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        Bundle gameBundle = new Bundle();
        gameBundle.putByteArray(MyGlobalVars.TAG_ID, byteArray);
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
}
