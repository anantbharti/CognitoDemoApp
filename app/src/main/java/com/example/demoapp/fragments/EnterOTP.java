package com.example.demoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.demoapp.R;

public class EnterOTP extends Fragment {

    public EnterOTP() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_o_t_p, container, false);
        EditText otp = view.findViewById(R.id.otp);
        Button submit = view.findViewById(R.id.submit_btn);
        return view;
    }
}