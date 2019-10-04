package com.example.if26new;

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
                //On va venir tester si l'addresse mail n'est mpas déjà utiliser par un compte
                boolean enter = false;
                Map<String, UserModel> testAdressMailAlreadyUse = model.getRegisterUsers();
                for (Map.Entry<String, UserModel> entry : testAdressMailAlreadyUse.entrySet()) {
                    if (entry.getKey().toString().equals(mailAddress)) {
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
            if ((isMail + isPassword) == 2) {
                model.registerUser(username, mailAddress, password);
            }
            if (isMail == 6) {
                return isMail;
            } else {
                return isMail + isPassword;
            }
        }
    }
    public Map<String, UserModel> getRegisterUsers(){
        return model.getRegisterUsers();
    }

}