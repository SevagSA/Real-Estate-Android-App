package com.example.realestateapplication.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.realestateapplication.Fragments.DownPaymentCalculationFragment;
import com.example.realestateapplication.Fragments.DownPaymentResultFragment;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;

public class DownPaymentActivity extends AppCompatActivity implements Communication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_payment);
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