package com.example.if26new;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;


public class SettingActivity extends AppCompatActivity{

    private ImageButton returnButton;
    private ConstraintLayout layout;
    private String wichFragment;
    private GradientDrawable gd;
    private Button apply;
    private int sizeColor;
    private Fragment fragmentTest;
    private SaveMyMusicDatabase db;
    private ImageView imageView;
    private TextView currentHexa;
    private EditText hexaStartText;
    private EditText hexaEndText;
    private EditText hexaButton;
    private Bitmap bitmap;
    private Button startBtn;
    private Button stopBtn;
    private Button colorbtn;
    private String hex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageView=findViewById(R.id.colorTab);
        currentHexa=findViewById(R.id.currentHexadecimal);
        layout=findViewById(R.id.secondLayout);
        returnButton=findViewById(R.id.returnMainMenu);
        hexaEndText=findViewById(R.id.hexadecimalForStopColor);
        hexaStartText=findViewById(R.id.hexadecimalForStartColor);
        hexaButton=findViewById(R.id.hexadecimalForButtonColor);

        startBtn=findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hexaStartText.setText(currentHexa.getText().toString());
                hexaStartText.setTextColor(currentHexa.getTextColors());
            }
        });
        stopBtn=findViewById(R.id.endButton);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hexaEndText.setText(currentHexa.getText().toString());
                hexaEndText.setTextColor(currentHexa.getTextColors());
            }
        });
        colorbtn=findViewById(R.id.colorbutton);
        colorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hexaButton.setText(currentHexa.getText().toString());
                hexaButton.setTextColor(currentHexa.getTextColors());
            }
        });
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE){
                    bitmap=imageView.getDrawingCache();
                    int pixel=bitmap.getPixel((int)event.getX(),(int)event.getY());

                    int r=Color.red(pixel);
                    int g=Color.green(pixel);
                    int b=Color.blue(pixel);

                    hex="#"+Integer.toHexString(pixel);
                    currentHexa.setText(hex);
                    currentHexa.setTextColor(Color.parseColor(hex));
                }
                return false;
            }
        });
        /*fragmentTest=new MainFragment();
        if (fragmentTest!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForColor, fragmentTest).addToBackStack(null).commit();
        }*/
        db=SaveMyMusicDatabase.getInstance(this);
        gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {db.userDao().getUserFromId(db.getActualUser()).getStartColorGradient(),db.userDao().getUserFromId(db.getActualUser()).getEndColorGradient()});
        gd.setCornerRadius(0f);
        layout.setBackground(gd);


        wichFragment=getIntent().getExtras().getString("FRAGMENT_NAME");

        apply=findViewById(R.id.applyColorChange);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean test=false;
                if (hexaStartText.equals("")){
                    hexaStartText.setError("Please choose a color");
                    test=true;
                }if(hexaEndText.equals("")){
                    hexaEndText.setError("Please choose a color");
                    test=true;
                }if(hexaButton.equals("")){
                    hexaButton.setError("Please choose a color");
                    test=true;
                }
                if(test==false){
                    int inter1=Color.parseColor(hexaStartText.getText().toString());
                    int inter2=Color.parseColor(hexaEndText.getText().toString());
                    int inter3=Color.parseColor(hexaButton.getText().toString());
                    gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {inter1,inter2});
                    gd.setCornerRadius(0f);
                    layout.setBackground(gd);
                    startBtn.setBackgroundColor(inter3);
                    stopBtn.setBackgroundColor(inter3);
                    colorbtn.setBackgroundColor(inter3);
                    //Change all button
                    DrawableCompat.setTint(returnButton.getDrawable(),inter3);
                    db.userDao().UpdateUserColor(inter1,inter2,inter3,db.getActualUser());
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnMethod();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnMethod();
    }
    public void returnMethod(){
        Bundle bundle=new Bundle();
        bundle.putString("FRAGMENT_NAME",wichFragment);
        Intent home = new Intent(SettingActivity.this, HomeActivity.class);
        home.putExtras(bundle);
        startActivity(home);
    }
}
