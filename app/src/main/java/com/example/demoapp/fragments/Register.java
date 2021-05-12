package com.example.demoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.example.demoapp.CognitoSettings;
import com.example.demoapp.MainActivity;
import com.example.demoapp.R;

import java.awt.font.TextAttribute;

public class Register extends Fragment {

    final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

    public Register() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText emailId=view.findViewById(R.id.user_email);
        final EditText phoneNo=view.findViewById(R.id.phone_no);
        final EditText password=view.findViewById(R.id.password);
        TextView signIn = view.findViewById(R.id.sign_in);
        Button register=view.findViewById(R.id.register);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn signUp = new LogIn();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main,signUp);
                ft.commit();
            }
        });

        final SignUpHandler signupCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                Toast.makeText(getActivity(),signUpResult.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getActivity(),exception.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                userAttributes.addAttribute("email",emailId.getText().toString());
                userAttributes.addAttribute("phone_number",phoneNo.getText().toString());

                CognitoSettings cognitoSettings = new CognitoSettings(getActivity());

                cognitoSettings.getUserPool().signUpInBackground(phoneNo.getText().toString(),password.getText().toString(),
                        userAttributes,null,signupCallback);
            }
        });
        return view;
    }
}