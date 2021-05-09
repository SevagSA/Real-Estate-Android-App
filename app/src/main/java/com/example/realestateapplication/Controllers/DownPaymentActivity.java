package com.example.realestateapplication.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Fragments.AboutDialogFragment;
import com.example.realestateapplication.Fragments.DownPaymentCalculationFragment;
import com.example.realestateapplication.Fragments.DownPaymentResultFragment;
import com.example.realestateapplication.Fragments.ProfileDialogFragment;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;
import com.google.android.material.navigation.NavigationView;

public class DownPaymentActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, Communication {
    /**
     * 3 input fields
     * 1. The amount of the house
     * 2. The percentage of down payment
     * 3. The calculated total amount you need to pay w/that percentage
     * <p>
     * 2 way communication:
     * <p>
     * When the total amount of the house is typed (onKeyBoardType event listener)
     * and the percentage of down payment section is not blank (put a default percentage of 5% and if the user deletes everything from that input field, put the 5% again, so basically, it's either gonna be the user's input value of 5 percentage. Also, user can't put negative.
     * calculate the total amount tou need to pay and display it in the 3rd input field (this will be diaplsued in real time, i.e. as the user types the house price).
     * <p>
     * Total amount of house (including percentage) and the calculated total are separate fragments.
     * This was 1 way communication.
     * <p>
     * When the user changes the calcilate total amoinut, the total house price will change as well
     * based on the percentage.
     * So, if:
     * Current total house price: 100,000$
     * Current down payment %: 5%
     * Calculated total: 5,000$
     * Then, when the user changes the total (5,000$), say to 10,000$ and if the
     * Current down payment stays the same (5%), then the Current total house price
     * should be update to 200,000$ (10000/0.05=200,000$)
     * <p>
     * So the Total Fragment is passing the new total to the Current total house price
     * fragment, where the latter is updating the Current total house price.
     * <p>
     * That is 2 way communication
     */
    private String chosenLang;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_payment);
        chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = findViewById(R.id.homeDrawerMenu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void calculateTotalAmount(double totalHousePrice, double downPayment) {
        FragmentManager fm = getSupportFragmentManager();
        DownPaymentResultFragment fragment = (DownPaymentResultFragment) fm.findFragmentById(R.id.resultFragmentFromMain);
        fragment.calculateTotalAmount(totalHousePrice, downPayment);
    }

    @Override
    public void updateHousePrice(double calculatedMonthlyAmount) {
        FragmentManager fm = getSupportFragmentManager();
        DownPaymentCalculationFragment fragment = (DownPaymentCalculationFragment) fm.findFragmentById(R.id.calculationFragmentFromMain);
        fragment.updateHousePrice(calculatedMonthlyAmount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_list_property:
                startActivity(new Intent(this, ListPropertyActivity.class));
                break;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getString(R.string.about);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        getString(R.string.share_title));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
                break;
            case R.id.nav_list_of_agents:
                startActivity(new Intent(this, AgentListActivity.class));
                break;
            case R.id.nav_home_page:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.nav_down_payment_calculator:
                startActivity(new Intent(this, DownPaymentActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            startActivity(new Intent(this, SearchPropertyActivity.class));
        } else if (id == R.id.likeItem) {
            startActivity(new Intent(this, LikedListingsActivity.class));
        } else if (id == R.id.about) {
            AboutDialogFragment dialogFragment = new AboutDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "AboutDialogFragment");
        } else if (id == R.id.logout) {
            SharedPreferences shared = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(getString(R.string.login_shared_pref), "false");
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.your_profile) {
            ProfileDialogFragment dialogFragment = new ProfileDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "ProfileDialogFragment");
        } else if (id == R.id.changeLanguage) {
            LocaleHelper localeHelper = new LocaleHelper(this);
            if (chosenLang.equals("hy")) {
                localeHelper.changeLocale("en", "User", R.string.selected_language);
            } else {
                localeHelper.changeLocale("hy", "User", R.string.selected_language);
            }
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                    .getString(getString(R.string.selected_language), null);
            Toast.makeText(this, chosenLang, Toast.LENGTH_LONG).show();
            invalidateOptionsMenu();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.changeLanguage);
        if (chosenLang.equals("hy")) {
            item.setTitle(R.string.english);
        } else {
            item.setTitle(R.string.armenian);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}