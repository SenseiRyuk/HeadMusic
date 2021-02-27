package com.example.if26new.Setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.if26new.HomeActivity;
import com.example.if26new.SignIn_LogIn.MainActivity;
import com.example.if26new.R;
import com.example.if26new.SaveMyMusicDatabase;


public class SettingActivity extends AppCompatActivity{

    private ImageButton returnButton;
    private ConstraintLayout layout;
    private String wichFragment;
    private GradientDrawable gd;
    private Button apply;
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
    private ImageButton logOut;
    private Button defaultColor;
    private String btnName;
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

        logOut=findViewById(R.id.logOutBtn);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connexionActivity =new Intent(SettingActivity.this, MainActivity.class);
                startActivity(connexionActivity);
            }
        });
        defaultColor=findViewById(R.id.defaultColor);
        defaultColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {R.color.StartColor,R.color.EndColor});
                gd.setCornerRadius(0f);
                layout.setBackground(gd);

                startBtn.setBackground(roundbuttonSetting(0xFF4A86E8,startBtn.getText().toString()));
                stopBtn.setBackground(roundbuttonSetting(0xFF4A86E8,stopBtn.getText().toString()));
                colorbtn.setBackground(roundbuttonSetting(0xFF4A86E8,colorbtn.getText().toString()));
                apply.setBackground(roundbuttonSetting(0xFF4A86E8,apply.getText().toString()));
                DrawableCompat.setTint(logOut.getDrawable(),0xFF4A86E8);
                defaultColor.setBackground(roundbuttonSetting(0xFF4A86E8,defaultColor.getText().toString()));
                DrawableCompat.setTint(returnButton.getDrawable(),0xFF4A86E8);
                db.userDao().UpdateUserColor(0xFF482834,0xFF1C3766,0xFF4A86E8,db.getActualUser());
            }
        });
        startBtn=findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentHexa.getText().toString().equals("#0")){
                    hexaStartText.setText(currentHexa.getText().toString());
                    hexaStartText.setTextColor(currentHexa.getTextColors());
                }
            }
        });
        stopBtn=findViewById(R.id.endButton);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentHexa.getText().toString().equals("#0")){
                    hexaEndText.setText(currentHexa.getText().toString());
                    hexaEndText.setTextColor(currentHexa.getTextColors());
                }
            }
        });
        colorbtn=findViewById(R.id.colorbutton);
        colorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentHexa.getText().toString().equals("#0")){
                    hexaButton.setText(currentHexa.getText().toString());
                    hexaButton.setTextColor(currentHexa.getTextColors());
                }
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
                    if (!hex.equals("#0")){
                        currentHexa.setTextColor(Color.parseColor(hex));
                    }
                }
                return false;
            }
        });
        /*fragmentTest=new HomeFragment();
        if (fragmentTest!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentForColor, fragmentTest).addToBackStack(null).commit();
        }*/
        db=SaveMyMusicDatabase.getInstance(this);
        gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {db.userDao().getUserFromId(db.getActualUser()).getStartColorGradient(),db.userDao().getUserFromId(db.getActualUser()).getEndColorGradient()});
        gd.setCornerRadius(0f);
        layout.setBackground(gd);

        wichFragment=getIntent().getExtras().getString("FRAGMENT_NAME");

        apply=findViewById(R.id.applyColorChange);

        setBtnColor();


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean gradient=false;
                boolean colorButton=false;
                if (hexaStartText.getText().toString().equals("")){
                    hexaStartText.setError("Please choose a color");
                    gradient=true;
                }if(hexaEndText.getText().toString().equals("")){
                    hexaEndText.setError("Please choose a color");
                    gradient=true;
                }if(hexaButton.getText().toString().equals("")){
                    hexaButton.setError("Please choose a color");
                    colorButton=true;
                }
                if((gradient==false)&&(colorButton==false)){
                    int inter1=Color.parseColor(hexaStartText.getText().toString());
                    int inter2=Color.parseColor(hexaEndText.getText().toString());
                    int inter3=Color.parseColor(hexaButton.getText().toString());
                    gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {inter1,inter2});
                    gd.setCornerRadius(0f);
                    layout.setBackground(gd);

                    startBtn.setBackground(roundbuttonSetting(inter3,startBtn.getText().toString()));
                    stopBtn.setBackground(roundbuttonSetting(inter3,stopBtn.getText().toString()));
                    colorbtn.setBackground(roundbuttonSetting(inter3,colorbtn.getText().toString()));
                    apply.setBackground(roundbuttonSetting(inter3,apply.getText().toString()));
                    DrawableCompat.setTint(logOut.getDrawable(),inter3);
                    defaultColor.setBackground(roundbuttonSetting(inter3,defaultColor.getText().toString()));
                    //Change all button
                    DrawableCompat.setTint(returnButton.getDrawable(),inter3);
                    db.userDao().UpdateUserColor(inter1,inter2,inter3,db.getActualUser());
                }else if ((gradient==false)&&(colorButton==true)){
                    int inter1=Color.parseColor(hexaStartText.getText().toString());
                    int inter2=Color.parseColor(hexaEndText.getText().toString());
                    gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {inter1,inter2});
                    gd.setCornerRadius(0f);
                    layout.setBackground(gd);
                    db.userDao().UpdateUserColor(inter1,inter2,db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),db.getActualUser());
                }else if ((gradient==true)&&(colorButton==false)){
                    int inter3=Color.parseColor(hexaButton.getText().toString());

                    startBtn.setBackground(roundbuttonSetting(inter3,startBtn.getText().toString()));
                    stopBtn.setBackground(roundbuttonSetting(inter3,stopBtn.getText().toString()));
                    colorbtn.setBackground(roundbuttonSetting(inter3,colorbtn.getText().toString()));
                    apply.setBackground(roundbuttonSetting(inter3,apply.getText().toString()));
                    DrawableCompat.setTint(logOut.getDrawable(),inter3);
                    defaultColor.setBackground(roundbuttonSetting(inter3,defaultColor.getText().toString()));

                    DrawableCompat.setTint(returnButton.getDrawable(),inter3);
                    db.userDao().UpdateUserColor(db.userDao().getUserFromId(db.getActualUser()).getStartColorGradient(),db.userDao().getUserFromId(db.getActualUser()).getEndColorGradient(),inter3,db.getActualUser());
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnMethod();
            }
        });
    }

    public LayerDrawable roundbuttonSetting(int colorBackground,String Text){
    // Initialize two float arrays
        float[] outerRadii = new float[]{75,75,75,75,75,75,75,75};
        float[] innerRadii = new float[]{75,75,75,75,75,75,75,75};
        // Set the shape background
        ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null,
                innerRadii
        ));
        backgroundShape.getPaint().setColor(colorBackground); // background color

        // Initialize an array of drawables
        Drawable[] drawables = new Drawable[]{
                backgroundShape
        };
        Paint paint = new Paint();

        Canvas canvas = new Canvas();
        canvas.drawText(Text, 0, drawables[0].getMinimumHeight()/2, paint);

        drawables[0].draw(canvas);
        // Initialize a layer drawable object
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        return layerDrawable;
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
    public void setBtnColor(){
        startBtn.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),startBtn.getText().toString()));
        stopBtn.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),stopBtn.getText().toString()));
        colorbtn.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),colorbtn.getText().toString()));
        apply.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),apply.getText().toString()));
        DrawableCompat.setTint(logOut.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
        defaultColor.setBackground(roundbuttonSetting(db.userDao().getUserFromId(db.getActualUser()).getButtonColor(),defaultColor.getText().toString()));
        DrawableCompat.setTint(returnButton.getDrawable(),db.userDao().getUserFromId(db.getActualUser()).getButtonColor());
    }
}
