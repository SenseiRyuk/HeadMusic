package com.example.if26new;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.UserModel;


public class SignInActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private EditText password;
    private EditText username;
    private EditText mailAddress;
    private ImageButton confirm;
    private ImageButton delete;
    private ImageButton returnMainMenu;
    private EditText confirmPassword;
    private SaveMyMusicDatabase db;
    private UserModel userModel;
    private Switch fingerPrintSwitch;
    private Dialog eraseAccount;
    private ImageButton confirmEraseAccount;
    private ImageButton deleteEraseAccount;
    private TextView textEraseAccount;
    private int positionFingerPrintAccount;
    private UserModel[] allUsers;
    private boolean exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mainLayout=findViewById(R.id.mainSignLayout);

        db=SaveMyMusicDatabase.getInstance(this);
        allUsers= db.userDao().loadAllUsers();
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {0xFF482834,0xFF1C3766});
        gd.setCornerRadius(0f);
        mainLayout.setBackground(gd);

        final ControlerLayouts controler = ControlerLayouts.getInstance();

        password=findViewById(R.id.fieldForPasswordSignIn);
        password.setText("");
        confirmPassword=findViewById(R.id.fieldForConfirmPassword);
        confirmPassword.setText("");
        username=findViewById(R.id.fieldForUsernameSignIn);
        mailAddress=findViewById(R.id.fieldForMailAddressSignIn);
        confirm=findViewById(R.id.confirm);
        fingerPrintSwitch=findViewById(R.id.enableFingerPrintRecognition);
        db=SaveMyMusicDatabase.getInstance(this);
        eraseAccount=new Dialog(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailAddress.setText("");
                password.setText("");
                username.setText("");
                confirmPassword.setText("");
            }
        });
        returnMainMenu=findViewById(R.id.returnLogMenu);
        returnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainLayout);
            }
        });
    }


    public int testMailAddressAndPassword(String mailAddress, String password, String username){
        int isMail = 0;
        int isPassword = 0;
        if (username.isEmpty()){
            return 7;
        }else {
            int pos = mailAddress.indexOf('@');
            if (pos == -1) {
                isMail = isMail + 2;
            } else {
                //On va venir tester si l'addresse mail n'est pas déjà utiliser par un compte
                boolean enter = false;
                for (int i=0;i<allUsers.length;i++) {
                    if (allUsers[i].getMailAdress().toString().equals(mailAddress)){
                        enter = true;
                        isMail = 6;
                    }
                }
                if (enter == false) {
                    isMail++;
                }
            }
            boolean isNum = false;
            for (int num = 0; num <= 9; num++) {
                String stringNum = Integer.toString(num);
                if (password.indexOf(stringNum) != -1) {
                    isNum = true;
                }
            }
            if (isNum == false) {
                isPassword = isPassword + 3;
            } else {
                isPassword++;
                //on va le passer à notre base de donnée
            }
            if (isMail == 6) {
                return isMail;
            } else {
                return isMail + isPassword;
            }
        }
    }
    public void showPopUp(){
        eraseAccount.setContentView(R.layout.erase_account_pop_up);
        confirmEraseAccount=eraseAccount.findViewById(R.id.acceptEraseAccount);
        deleteEraseAccount=eraseAccount.findViewById(R.id.refuseEraseAccount);
        textEraseAccount=eraseAccount.findViewById(R.id.textEraseAccount);
        textEraseAccount.setText("An account register with the mail address :'"+allUsers[positionFingerPrintAccount].getMailAdress()+"'\nhas the finger print register on your phone\nDo you want to delete this account");
        textEraseAccount.setTextSize(18);
        confirmEraseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.userDao().deleteUser(allUsers[positionFingerPrintAccount].getId());
                userModel=new UserModel(username.getText().toString(),password.getText().toString(),mailAddress.getText().toString(),R.drawable.default_avatar, Color.parseColor("#482834"),Color.parseColor("#1C3766"),Color.parseColor("#4A86E8"),true);
                db.userDao().createUser(userModel);
                PlaylistModel playlistUser=new PlaylistModel(db.userDao().getUserFromUsername(username.getText().toString()).getId(),"Favorite",R.drawable.like);
                db.mPlaylistDao().insertPlaylist(playlistUser);
                Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainLayout);
                eraseAccount.dismiss();
            }
        });
        deleteEraseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel=new UserModel(username.getText().toString(),password.getText().toString(),mailAddress.getText().toString(),R.drawable.default_avatar, Color.parseColor("#482834"),Color.parseColor("#1C3766"),Color.parseColor("#4A86E8"),false);
                db.userDao().createUser(userModel);
                PlaylistModel playlistUser=new PlaylistModel(db.userDao().getUserFromUsername(username.getText().toString()).getId(),"Favorite",R.drawable.like);
                db.mPlaylistDao().insertPlaylist(playlistUser);
                Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainLayout);
                eraseAccount.dismiss();
            }
        });
        eraseAccount.show();
    }
    public void register(){
        if(confirmPassword.getText().toString().equals(password.getText().toString())){
            int isValid=testMailAddressAndPassword(mailAddress.getText().toString(),password.getText().toString(),username.getText().toString());
            switch (isValid) {
                case 2:
                    System.out.println("JE SUIS DANS LE REGISTER");
                    if (fingerPrintSwitch.isChecked()){
                        boolean isAlreadyAccountWithFingerPrint=false;
                        positionFingerPrintAccount=0;
                        for(int i=0;i<allUsers.length;i++){
                            if(allUsers[i].isFingerPrint()==true){
                                isAlreadyAccountWithFingerPrint=true;
                                positionFingerPrintAccount=i;
                            }
                        }
                        if(isAlreadyAccountWithFingerPrint==true){
                           showPopUp();
                        }else{
                            userModel=new UserModel(username.getText().toString(),password.getText().toString(),mailAddress.getText().toString(),R.drawable.default_avatar, Color.parseColor("#482834"),Color.parseColor("#1C3766"),Color.parseColor("#4A86E8"),true);
                            db.userDao().createUser(userModel);
                            PlaylistModel playlistUser=new PlaylistModel(db.userDao().getUserFromUsername(username.getText().toString()).getId(),"Favorite",R.drawable.like);
                            db.mPlaylistDao().insertPlaylist(playlistUser);
                            Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(mainLayout);
                        }
                    }
                    else{
                        userModel=new UserModel(username.getText().toString(),password.getText().toString(),mailAddress.getText().toString(),R.drawable.default_avatar, Color.parseColor("#482834"),Color.parseColor("#1C3766"),Color.parseColor("#4A86E8"),false);
                        db.userDao().createUser(userModel);
                        PlaylistModel playlistUser=new PlaylistModel(db.userDao().getUserFromUsername(username.getText().toString()).getId(),"Favorite",R.drawable.like);
                        db.mPlaylistDao().insertPlaylist(playlistUser);
                        Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(mainLayout);
                    }
                    break;
                case 3:
                    mailAddress.setError("Wrong mail Address");
                    break;
                case 4:
                    password.setError("You need to have at least one numeric in your password");
                    break;
                case 5:
                    mailAddress.setError("Wrong mail Address");
                    password.setError("You need to have at least one numeric in your password");
                    break;
                case 6:
                    Toast toast = Toast.makeText(getApplicationContext(), "This mail address is already used", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case 7 :
                    username.setError("Please enter a Username");
                    mailAddress.setError("Wrong mail Address");
                    password.setError("You need to have at least one numeric in your password");
                    break;
                default:
                    break;
            }
        }else{
            confirmPassword.setError("wrong password");
        }

    }
}
