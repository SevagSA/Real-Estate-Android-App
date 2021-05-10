package com.example.realestateapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class DownPaymentCalculationFragment extends Fragment {

    private Communication comm;
    private EditText housePrice;

    public DownPaymentCalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_down_payment_calculation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communication) getActivity();
        housePrice = getActivity().findViewById(R.id.housePrice);

        Button calculateDownPaymentBtn = getActivity().findViewById(R.id.calculateDownPaymentBtn);
        calculateDownPaymentBtn.setOnClickListener(v -> {
            EditText downPayment = getActivity().findViewById(R.id.downPaymentPercentage);
            String housePriceStr = housePrice.getText().toString();
            String downPaymentStr = downPayment.getText().toString();
            boolean isValid = true;

            if (housePriceStr.isEmpty()) {
                housePrice.setError(getString(R.string.house_price_required));
                isValid = false;
            }
            if (downPaymentStr.isEmpty()) {
//                If no value is input for the percentage, the default percentage will be 5%
                downPaymentStr = "5";
            }

            if (housePriceStr.contains("$")) {
                housePriceStr = housePriceStr.replaceAll("\\$", "");
            }

            if (housePriceStr.contains(",")) {
                housePriceStr = housePriceStr.replaceAll(",", "");
            }

            if (downPaymentStr.contains("%")) {
                downPaymentStr = downPaymentStr.replaceAll("%", "");
            }

            if (housePriceStr.contains("ARM")) {
                housePriceStr = housePriceStr.replaceAll("ARM", "");
            }

            if (housePriceStr.contains("CAN")) {
                housePriceStr = housePriceStr.replaceAll("CAN", "");
            }

            if (isValid) {
                double housePriceDouble = Double.parseDouble(housePriceStr);
                // Get percentage in decimal format
                double downPaymentDouble = Double.parseDouble(downPaymentStr) / 100;
                comm.calculateTotalAmount(housePriceDouble, downPaymentDouble);
            } else {
                Toast.makeText(getActivity(), R.string.fix_the_errors, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateHousePrice(double monthlyAmount, double downPayment) {
        Log.d("monthlyAmount", monthlyAmount + "");
        Log.d("downPayment", downPayment + "");
        double total = monthlyAmount / downPayment;
        LocaleHelper localHelper = new LocaleHelper(getActivity());
        String currency = localHelper.getMoneyInCurrentLocale(total, "User", R.string.selected_language);
        housePrice.setText(currency);
    }
}