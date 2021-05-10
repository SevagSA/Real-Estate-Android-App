package com.example.realestateapplication.Fragments;

import android.content.Context;
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

import com.example.localehelper.LocaleHelper;
import com.example.realestateapplication.Interfaces.Communication;
import com.example.realestateapplication.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class DownPaymentResultFragment extends Fragment {

    private Communication comm;
    private EditText result;
    private Double downPayment = 0.05;

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
        comm = (Communication) getActivity();
        result = getActivity().findViewById(R.id.calulationResult);
        result.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() != KeyEvent.ACTION_UP) {
                return false;
            }
            if (event.getAction() == KeyEvent.ACTION_UP && (
                    keyCode == KeyEvent.KEYCODE_1
                            || keyCode == KeyEvent.KEYCODE_2
                            || keyCode == KeyEvent.KEYCODE_3
                            || keyCode == KeyEvent.KEYCODE_4
                            || keyCode == KeyEvent.KEYCODE_5
                            || keyCode == KeyEvent.KEYCODE_6
                            || keyCode == KeyEvent.KEYCODE_7
                            || keyCode == KeyEvent.KEYCODE_8
                            || keyCode == KeyEvent.KEYCODE_9
                            || keyCode == KeyEvent.KEYCODE_0
                            || keyCode == KeyEvent.KEYCODE_PERIOD
                            || keyCode == KeyEvent.KEYCODE_COMMA)
            ) {
                String resultStr = result.getText().toString();
                if (resultStr.length() != 0) {
                    // This should never happen
                    if (Character.toString(resultStr.charAt(result.length() - 1)).equals("$")) {
                        resultStr = resultStr.substring(0, result.length() - 1);
                    }


                    if (resultStr.contains(".")) {
                        int dotIdx = resultStr.indexOf(".");
                        String resultStrDecimals = resultStr.substring(resultStr.indexOf("."));
                        int lengthOfDecimalPlaces = resultStrDecimals.length();
                        if (lengthOfDecimalPlaces > 2) {
//                            We want 2 decimal only. Remove everything after the 2nd decimal.
//                            The "." is included, hence why we do (0, 3) and not (0, 2)
                            resultStrDecimals = resultStrDecimals.substring(0, 3);
                        }
                        Log.d("resultStrDecimals", resultStrDecimals);
                        resultStr = resultStr.substring(0, dotIdx);
                        resultStr += resultStrDecimals;
                    }
                }

                resultStr = resultStr.replaceAll("ARM", "");
                resultStr = resultStr.replaceAll("CAN", "");
                resultStr = resultStr.replaceAll(",", "");
                double resultDouble = Double.parseDouble(resultStr);
                comm.updateHousePrice(resultDouble, downPayment);
                return true;
            }
            return false;
        });
    }

    public void calculateTotalAmount(double totalHousePrice, double downPayment) {
        this.downPayment = downPayment;
        double total = totalHousePrice * downPayment;
        LocaleHelper localHelper = new LocaleHelper(getActivity());
        String currency = localHelper.getMoneyInCurrentLocale(total, "User", R.string.selected_language);
        result.setText(currency);
    }
}