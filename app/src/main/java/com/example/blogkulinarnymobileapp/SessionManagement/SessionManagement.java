package com.example.blogkulinarnymobileapp.SessionManagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.blogkulinarnymobileapp.Models.Ranks;
import com.example.blogkulinarnymobileapp.Models.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_ID_KEY = "session_id";
    String SESSION_USERNAME_KEY = "session_username";

    String SESSION_EMAIL_KEY = "session_email";
    private static SessionManagement instance;

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Zapisywanie sesji
    public void saveSession(int rank, int id, String username, String mail){
        editor.putInt(SESSION_KEY,rank);
        editor.putInt(SESSION_ID_KEY,id);
        editor.putString(SESSION_USERNAME_KEY, username);
        editor.putString(SESSION_EMAIL_KEY, mail);
        editor.commit();
    }

    //Pobieranie sesji (tego używamy, że sprawdzić czy jest zalogowany admin czy normalny użytkownik)
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public int getSessionId(){
        return sharedPreferences.getInt(SESSION_ID_KEY, -1);
    }

    public String getSessionUsername() {
        return sharedPreferences.getString(SESSION_USERNAME_KEY, "");
    }

    public String getSessionEmail() {
        return sharedPreferences.getString(SESSION_EMAIL_KEY, "");
    }

    //Używanie do wylogowania
    public void removeSession(){
        editor.clear();
        editor.commit();
        editor.remove(SESSION_USERNAME_KEY);
        editor.remove(SESSION_EMAIL_KEY);
    }

    public static SessionManagement getInstance(Context context){
        if(instance == null){
            instance = new SessionManagement(context);
        }
        return instance;
    }
}
