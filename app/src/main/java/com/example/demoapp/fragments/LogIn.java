package com.example.demoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.util.CognitoServiceConstants;
import com.example.demoapp.CognitoSettings;
import com.example.demoapp.R;

import java.util.HashMap;

public class LogIn extends Fragment {

    private CognitoUserPool cup;
    public LogIn() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        final EditText loginPhone=view.findViewById(R.id.login_phone);
        Button logIn = view.findViewById(R.id.log_in);
        TextView signUp = view.findViewById(R.id.sign_up);
        final TextView message = view.findViewById(R.id.text_msg);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register signUp = new Register();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main,signUp);
                ft.commit();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CognitoSettings cognitoSettings= new CognitoSettings(getContext());

                cup = cognitoSettings.getUserPool();
                CognitoUser cognitoUser = cup.getCurrentUser();
                if(cognitoUser!=null)
                    cognitoUser.signOut();
                AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
                    @Override
                    public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                        Toast.makeText(getContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                        Toast.makeText(getContext(),"Details fetched",Toast.LENGTH_SHORT).show();
                        final HashMap<String, String> authParameters = new HashMap<>();
                        AuthenticationDetails authenticationDetails = new AuthenticationDetails(loginPhone.getText().toString(), authParameters, null);
                        authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                        authenticationContinuation.continueTask();
                    }

                    @Override
                    public void getMFACode(MultiFactorAuthenticationContinuation continuation) {
                    }

                    @Override
                    public void authenticationChallenge(ChallengeContinuation continuation) {
                        Toast.makeText(getContext(),"OTP sent",Toast.LENGTH_SHORT).show();
                        continuation.setChallengeResponse(CognitoServiceConstants.CHLG_RESP_ANSWER, "1133");
                        continuation.continueTask();
                    }

                    @Override
                    public void onFailure(Exception exception) {
//                        Toast.makeText(getContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
                        message.setText(exception.getMessage());
                    }
                };
                cup.getUser(loginPhone.getText().toString()).getSessionInBackground(authenticationHandler);
            }
        });

        return view;
    }

}