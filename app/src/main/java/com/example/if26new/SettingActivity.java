package com.example.if26new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class SettingActivity extends AppCompatActivity {

    ImageButton button1;
    Switch nightView;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        layout=findViewById(R.id.secondLayout);
        button1=findViewById(R.id.returnMainMenu);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent firtslayoutDisplay = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(firtslayoutDisplay);
            }
        });
        nightView=findViewById(R.id.nightMode);
        nightView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // MainActivity firstActivity =MainActivity.getInstance();
                if (isChecked) {
                    nightView.setTextColor(Color.WHITE);
                    nightView.setText("Day View");
                    layout.setBackgroundColor(Color.BLACK);
                } else {
                    nightView.setTextColor(Color.BLACK);
                    nightView.setText("Night View");
                    layout.setBackgroundColor(Color.WHITE);
                }
                Intent intent=new Intent();
                //intent.putExtra("MESSAGE",message);
                setResult(2,intent);
                //finish();//finishing activity
            }
        });
    }
}
