package com.example.if26new.SignIn_LogIn;

import com.example.if26new.Model.UserModel;

import java.util.Map;

public class ControlerLayouts{

    private ControlerLayouts()
    {}
    private static ControlerLayouts INSTANCE = new ControlerLayouts();

    public static ControlerLayouts getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ControlerLayouts();
        }
        return INSTANCE;
    }
    private ModelProject model=new ModelProject();


    public Map<String, UserModel> getRegisterUsers(){
        return model.getRegisterUsers();
    }

}