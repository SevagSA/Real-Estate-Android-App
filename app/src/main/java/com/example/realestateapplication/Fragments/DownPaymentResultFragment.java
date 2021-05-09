package com.example.realestateapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;


public class DownPaymentResultFragment extends Fragment /**implements KeyEvent.Callback*/ {

    private Communication comm;
    private EditText result;

    public DownPaymentResultFragment() {
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
        return inflater.inflate(R.layout.fragment_down_payment_calculation_result, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (Communication)getActivity();
        result = getActivity().findViewById(R.id.calulationResult);
        result.setOnKeyListener((v, keyCode, event) -> {
            Log.d("onkey", keyCode + "");
            double newResult = 1;
            comm.updateHousePrice(newResult);
            return true;
        });
    }

    public void calculateTotalAmount(double totalHousePrice, double downPayment) {
        result.setText((totalHousePrice + downPayment) + "");
    }
}