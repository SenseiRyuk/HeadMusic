package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.if26new.Model.UserModel;

public class MainActivity extends AppCompatActivity {

    private ImageButton settings;
    private ConstraintLayout firstLayout;
    private EditText password;
    private EditText username;
    private Button connexion;
    private Button signIN;
    private SaveMyMusicDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        db=SaveMyMusicDatabase.getInstance(this);
        final ControlerLayouts controler = ControlerLayouts.getInstance();

        firstLayout = findViewById(R.id.fristLayout);
        firstLayout.setVisibility(ConstraintLayout.VISIBLE);

       /* settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent secondlayoutDisplay = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(secondlayoutDisplay, 3);
            }
        });*/
        password=findViewById(R.id.enterPassword);
        username=findViewById(R.id.enterUserName);
        connexion=findViewById(R.id.connexion);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel[] toto= db.userDao().loadAllUsers();
                for (int i=0;i<toto.length;i++){
                    System.out.println("passwor et mail address du compte  \n "+toto[i].getPassword()+" "+toto[i].getMailAdress());
                }
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
                    final ControlerLayouts controler=ControlerLayouts.getInstance();
                    UserModel []allUsers=db.userDao().loadAllUsers();
                    for(int i=0;i<allUsers.length;i++){
                        if ((password.getText().toString().equals(allUsers[i].getPassword().toString())) && ((username.getText().toString().equals(allUsers[i].getUsername().toString()) || (username.getText().toString().equals(allUsers[i].getMailAdress()))))){
                            isRegisterUser=true;
                            Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeActivity);
                        }
                    }
                    if (isRegisterUser==false){
                        Toast toast = Toast.makeText(getApplicationContext(), "Your Account doesn't exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                //Juste pour pas a avoir retaper à chaque fois
                //Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                //startActivity(homeActivity);
            }
        });
        signIN=findViewById(R.id.singIn);
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInActivity = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signInActivity);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            firstLayout.setBackgroundColor(Color.BLACK);
        }
    }
}