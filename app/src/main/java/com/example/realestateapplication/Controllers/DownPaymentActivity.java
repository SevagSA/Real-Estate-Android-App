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

public class DownPaymentActivity extends AppCompatActivity implements Communication {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_payment);
        chosenLang = getSharedPreferences("User", Context.MODE_PRIVATE)
                .getString(getString(R.string.selected_language), null);
    }

    @Override
    public void calculateTotalAmount(double totalHousePrice, double downPayment) {
        FragmentManager fm = getSupportFragmentManager();
        DownPaymentResultFragment fragment = (DownPaymentResultFragment) fm.findFragmentById(R.id.resultFragmentFromMain);
        fragment.calculateTotalAmount(totalHousePrice, downPayment);
    }

    @Override
    public void updateHousePrice(double monthlyAmount, double downPayment) {
        FragmentManager fm = getSupportFragmentManager();
        DownPaymentCalculationFragment fragment = (DownPaymentCalculationFragment) fm.findFragmentById(R.id.calculationFragmentFromMain);
        fragment.updateHousePrice(monthlyAmount, downPayment);
    }
}