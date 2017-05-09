package com.example.yazid.jamiah;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yazid on 3/10/17.
 */

public class User {

    private String userName;
    private String email;
    private Map<String,Boolean> involvedInJam = new HashMap<>();


    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String email) {
        this.email = email;
    }

    public User(String email,String userName) {
        this.email = email;
        this.userName = userName;
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
}
