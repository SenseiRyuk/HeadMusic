package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Map;

public class MainActivity extends AppCompatActivity {



    private ImageButton settings;
    private ConstraintLayout firstLayout;
    private EditText password;
    private EditText username;
    private Button connexion;
    private Button signIN;
    private TextView InformationLog;
    Toast errormMssage;
    /*private MainActivity()
    {}
    private static MainActivity INSTANCE = new MainActivity();

    public static MainActivity getInstance()
    {   if (INSTANCE == null)
    {   INSTANCE = new MainActivity();
    }
        return INSTANCE;
    }*/

    public void changeBackgroundColor(String color) {
        if (color.equals("Black")) {
            firstLayout.setBackgroundColor(Color.WHITE);
        } else if (color.equals("White")) {
            firstLayout.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Map<String, UserModel> ListOfUsers=controler.getRegisterUsers();
                    for(Map.Entry<String, UserModel> entry : ListOfUsers.entrySet()){
                        System.out.println(entry.getKey() + "  /  " + entry.getValue().getPassword() + "  /  " + entry.getValue().getUserName());
                        System.out.println(password.getText() + "  /  " + username.getText());
                        if ((password.getText().toString().equals(entry.getValue().getPassword())) && ((username.getText().toString().equals(entry.getValue().getUserName())) || (username.getText().toString().equals(entry.getKey())))){
                            isRegisterUser=true;
                            Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeActivity);
                        }
                    }
                    if (isRegisterUser==false){
                        InformationLog=findViewById(R.id.informationAccount);
                        Toast toast = Toast.makeText(getApplicationContext(), "Your Account doesn't exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                //Juste pour pas a avoir retaper à chaque fois
                Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeActivity);
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