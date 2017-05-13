package com.example.yazid.jamiah.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yazid on 3/3/17.
 */

public class Jamiah implements Serializable {

    private static int counter =0;

    public  int jamId; //for Serializable intents
    private String id;
    private int amount;
    private int months;
    private int numberOfPersons;
    private Date startDate;
    private Date endDate;
    private Map<String , User> memebers;

    public Jamiah()
    {
        this.jamId = counter++;
    }

    public Jamiah(int amount, int months,
                  int numberOfPersons, Date startDate , Date endDate)
    {

        this.amount = amount;
        this.months=months;
        this.numberOfPersons = numberOfPersons;
        this.startDate =startDate;
        this.endDate = endDate;
        this.memebers= new HashMap<>();
    }

    public Date getEndDate() {
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

    public Date getStartDate() {
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMemebers(Map<String, User> memebers) {
        this.memebers = memebers;
    }

}
