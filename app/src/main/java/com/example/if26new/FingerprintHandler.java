package com.example.if26new;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private Context context;
    private SaveMyMusicDatabase db;

    public FingerprintHandler(Context context) {
        this.context = context;
    }


    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal=new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.MAPS_RECEIVE)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        super.onAuthenticationSucceeded(result);
        db=SaveMyMusicDatabase.getInstance(context);
        UserModel[] allUsers= db.userDao().loadAllUsers();
        for(int i=0;i<allUsers.length;i++){
            if (allUsers[i].isFingerPrint()){
                db.setActualUser(allUsers[i].getId());
                if(allUsers[i].getUsername().equals("root")&&!db.mPlaylistDao().getPlaylistFromUserAndNameExist(db.getActualUser(),"Favorite")){
                    PlaylistModel playlistUser = new PlaylistModel(db.getActualUser(),"Favorite",R.drawable.like);
                    db.mPlaylistDao().insertPlaylist(playlistUser);
                }
                Bundle bundle=new Bundle();
                bundle.putString("FRAGMENT_NAME","MainFragment");
                Intent homeActivity = new Intent(context, HomeActivity.class);
                homeActivity.putExtras(bundle);
                context.startActivity(homeActivity);

            }
        }
    }
    @Override
    public void onAuthenticationFailed(){
        super.onAuthenticationFailed();
        Toast.makeText(context,"Authentication Failed",Toast.LENGTH_SHORT).show();

    }
}
