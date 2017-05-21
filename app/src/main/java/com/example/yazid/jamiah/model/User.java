package com.example.yazid.jamiah.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yazid on 3/10/17.
 */

public class User {

    private String userName;
    private String email;
    private boolean hasLoggedInWithPassword;
    //private Map<String,Boolean> involvedInJam = new HashMap();


    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String email) {
        this.email = email;
    }

    public User(String email,String userName) {
        this.email = email;
        this.userName = userName;
        this.hasLoggedInWithPassword = false;
    }

    public void setHasLoggedInWithPassword(boolean hasLoggedInWithPassword) {
        this.hasLoggedInWithPassword = hasLoggedInWithPassword;
    }

    public boolean getHasLoggedInWithPassword() {

        return hasLoggedInWithPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String password) {
        this.email = password;
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(email,"email");
        result.put(userName,"userName");

        return result;
    }
}
