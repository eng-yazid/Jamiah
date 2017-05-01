package com.example.yazid.jamiah;

/**
 * Created by yazid on 3/10/17.
 */

public class User {

    private int id;
    private String userName;
    private String password;

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public User(String userName) {
        this.userName = userName;
    }

    /*
          for Firebase database
        public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
     */
    public User() {



    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
