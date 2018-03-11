package br.com.fiap.a29scjdenisapp.model;

import android.content.SharedPreferences;

public class utils {

    public static String getLoginPrefs(SharedPreferences prefs){
        return prefs.getString("login","");
    }
    public static String getLoginSenhaPrefs(SharedPreferences prefs){
        return prefs.getString("senha","");
    }
}
