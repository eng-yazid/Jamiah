package com.example.yazid.jamiah.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yazid on 3/3/17.
 */

public class Jamiah implements Serializable {

    private static int counter =0;

    private  int jamId; //for Serializable intents
    private String id;
    private int amount;
    private int months;
    private int numberOfPersons;
    private String startDate;
    private String endDate;
    private String owner;
    private int remainingMonths;
    private Map<String , User> memebers;

    public Jamiah()
    {
        this.jamId = counter++;
    }

    public Jamiah(int amount, int months,
                  int numberOfPersons,String owner, String startDate , String endDate)
    {
        this.amount = amount;
        this.months=months;
        this.numberOfPersons = numberOfPersons;
        this.startDate =startDate;
        this.endDate = endDate;
        this.owner = owner;
        remainingMonths=0;
       // this.memebers= new HashMap<>();
    }


    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("months", months);
        result.put("numberOfPersons", numberOfPersons);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("owner", owner);

        return result;
    }
    // [END post_to_map]

    public String getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getMonths() {
        return months;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public Map<String, User> getMemebers() {
        return memebers;
    }

    public  void setId(String id) {
        this.id = id;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMemebers(Map<String, User> memebers) {
        this.memebers = memebers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getRemainingMonths() {
        return remainingMonths;
    }

    public void setRemainingMonths(int remainingMonths) {
        this.remainingMonths = remainingMonths;
    }
}
