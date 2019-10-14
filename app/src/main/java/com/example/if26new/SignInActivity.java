package com.example.if26new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Map;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mainLayout=findViewById(R.id.mainSignLayout);
        //mainLayout.setBackgroundColor(Color.DKGRAY);

        final ControlerLayouts controler = ControlerLayouts.getInstance();

        password=findViewById(R.id.fieldForPasswordSignIn);
        password.setText("");
        confirmPassword=findViewById(R.id.fieldForConfirmPassword);
        confirmPassword.setText("");
        username=findViewById(R.id.fieldForUsernameSignIn);
        mailAddress=findViewById(R.id.fieldForMailAddressSignIn);
        confirm=findViewById(R.id.confirm);
        db=SaveMyMusicDatabase.getInstance(this);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmPassword.getText().toString().equals(password.getText().toString())){
                    int isValid=testMailAddressAndPassword(mailAddress.getText().toString(),password.getText().toString(),username.getText().toString());
                    switch (isValid) {
                        case 2:
                            userModel=new UserModel(db.userDao().loadAllUsers().length+1,username.getText().toString(),password.getText().toString(),mailAddress.getText().toString());
                            db.userDao().createUser(userModel);
                            Intent mainLayout = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(mainLayout);
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
                    System.out.println("JE SROS");
                    confirmPassword.setError("wrong password");
                }

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
        UserModel[] allUsers=db.userDao().loadAllUsers();
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
}
