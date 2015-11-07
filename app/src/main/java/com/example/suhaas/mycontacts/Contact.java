package com.example.suhaas.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String mName;
    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;



    public String getName() {

        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


}
