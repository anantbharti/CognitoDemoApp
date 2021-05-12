package com.example.demoapp;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolId=" us-east-2_mQfd0hTrz";
    private String clientId="6jrvfo75eap4kf5ovu8lm650mj";
    private String clientSecret="1c627n1pdfvldhbnuuhp3vdbsl0cvdqi5ulk0f7gqhkc2b6a3tpa";
    private Regions cognitoRegion= Regions.US_EAST_2;

    private Context context;

    public CognitoSettings(Context context){
        this.context=context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return  new CognitoUserPool(context,userPoolId,clientId,clientSecret,cognitoRegion);
    }
}
