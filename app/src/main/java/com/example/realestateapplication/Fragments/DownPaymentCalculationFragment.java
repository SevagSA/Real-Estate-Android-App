package com.example.realestateapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;

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
        comm = (Communication)getActivity();
        housePrice = getActivity().findViewById(R.id.housePrice);
        EditText downPayment = getActivity().findViewById(R.id.downPaymentPercentage);

        String housePriceStr = housePrice.getText().toString();
        String downPaymentStr = downPayment.getText().toString();
        Button calculateDownPaymentBtn = getActivity().findViewById(R.id.calculateDownPaymentBtn);
//        removing the $
        housePriceStr = housePriceStr.substring(0, housePriceStr.length() - 1);
//        removing the %
        downPaymentStr = downPaymentStr.substring(0, downPaymentStr.length() - 1);

        String finalHousePriceStr = housePriceStr;
        String finalDownPaymentStr = downPaymentStr;
        calculateDownPaymentBtn.setOnClickListener(v -> {
//            Double housePriceDouble = Double.parseDouble(finalHousePriceStr);
//            Double downPaymentDouble = Double.parseDouble(finalDownPaymentStr);
            comm.calculateTotalAmount(1.1, 2.2);
        });
    }

    public void updateHousePrice(Double calculatedMonthlyAmount) {
//        TODO check how to do it in the current local ("en" vs "hy")
        housePrice.setText(String.format(calculatedMonthlyAmount.toString()));
    }
}