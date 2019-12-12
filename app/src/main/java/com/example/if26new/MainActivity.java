package com.example.if26new;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout firstLayout;
    private EditText password;
    private EditText username;
    private Button connexion;
    private Button signIN;
    private SaveMyMusicDatabase db;
    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        db=SaveMyMusicDatabase.getInstance(this);

        firstLayout = findViewById(R.id.fristLayout);
        firstLayout.setVisibility(ConstraintLayout.VISIBLE);
        firstLayout=findViewById(R.id.layoutLogin);

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF482834,0xFF1C3766});
        gd.setCornerRadius(0f);
        firstLayout.setBackground(gd);

        password=findViewById(R.id.enterPassword);
        username=findViewById(R.id.enterUserName);
        connexion=findViewById(R.id.connexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel[] allUsers= db.userDao().loadAllUsers();
                if (username.getText().toString().isEmpty()){
                    username.setError("Please enter a mail address");
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Please enter a password");
                }
                if ((username.getText().toString().isEmpty())&& (password.getText().toString().isEmpty())){
                    username.setError("Please enter a mail address");
                    password.setError("Please enter a password");
                }if ((username.getText().toString().isEmpty()==false)&& (password.getText().toString().isEmpty()==false)){
                    boolean isRegisterUser=false;
                    for(int i=0;i<allUsers.length;i++){
                        if ((password.getText().toString().equals(allUsers[i].getPassword().toString())) && ((username.getText().toString().equals(allUsers[i].getUsername().toString()) || (username.getText().toString().equals(allUsers[i].getMailAdress()))))){
                            db.setActualUser(allUsers[i].getId());
                            isRegisterUser=true;
                            if(username.getText().toString().equals("root")&&!db.mPlaylistDao().getPlaylistFromUserAndNameExist(db.getActualUser(),"Favorite")){
                                PlaylistModel playlistUser = new PlaylistModel(db.getActualUser(),"Favorite",R.drawable.like);
                                db.mPlaylistDao().insertPlaylist(playlistUser);
                            }
                            Bundle bundle=new Bundle();
                            bundle.putString("FRAGMENT_NAME","MainFragment");
                            Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                            homeActivity.putExtras(bundle);
                            startActivity(homeActivity);

                        }
                    }
                    if (isRegisterUser==false){
                        Toast toast = Toast.makeText(getApplicationContext(), "Your Account doesn't exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        signIN=findViewById(R.id.singIn);
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent signInActivity = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signInActivity);
            }
        });

        //For the fingerPrint Recognition
        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if(!fingerprintManager.isHardwareDetected()){
            Toast.makeText(getApplicationContext(),"Your device doesn't have a finger print recognition",Toast.LENGTH_SHORT).show();
        }else{
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MAPS_RECEIVE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Fingerprint authentication permission not enabled",Toast.LENGTH_SHORT).show();
            }else{
                // Checks whether lock screen security is enabled or not
                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(getApplicationContext(),"Lock screen security not enabled in Settings",Toast.LENGTH_SHORT).show();
                }else{
                    generateKey();
                    if (cipherInit()) {
                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuthentication(fingerprintManager, cryptoObject);
                    }
                }
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            firstLayout.setBackgroundColor(Color.BLACK);
        }
    }
    public void generateKey(){
        try {
            keyStore= KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        KeyGenerator keyGenerator=null;
        try {
            keyGenerator=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }

    }
    public boolean cipherInit(){
        try{
            cipher= Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try{
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
        }catch (IOException e1){
            e1.printStackTrace();
            return false;
        }catch (NoSuchAlgorithmException e1){
            e1.printStackTrace();
            return false;
        }catch (CertificateException e1){
            e1.printStackTrace();
            return false;
        }catch (InvalidKeyException e1){
            e1.printStackTrace();
            return false;
        }catch (UnrecoverableKeyException e1){
            e1.printStackTrace();
            return false;
        }catch (KeyStoreException e1){
            e1.printStackTrace();
            return false;
        }
        return true;
    }
}