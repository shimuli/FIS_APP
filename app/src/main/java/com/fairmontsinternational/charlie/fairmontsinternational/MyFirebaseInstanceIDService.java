package com.fairmontsinternational.charlie.fairmontsinternational;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService{
    @Override
    public void onNewToken(String s){
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }
}

