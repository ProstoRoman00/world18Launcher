package ru.world18.a18worldlauncher;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class User {
    int id;
    String login;
    String password;
    String status="No";
    String sess_id="";
    public User(){
        this.id=0;
        this.login="";
        this.password="";
        this.status="created";
    }
    public void setLogin(){
        //static_vars.activity.mSettings
        SharedPreferences.Editor editor = static_vars.activity.mSettings.edit();
        editor.putString("login",login);
        editor.putString("password",password);
        editor.putString("status","saved");
        editor.putString("sess_id",sess_id);
        editor.apply();
    }
    public User exit(){
        SharedPreferences.Editor editor = static_vars.activity.mSettings.edit();
        editor.clear();
        editor.apply();
        return new User();
    }
}