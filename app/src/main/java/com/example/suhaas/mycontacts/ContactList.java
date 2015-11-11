package com.example.suhaas.mycontacts;

import java.util.ArrayList;

public class ContactList extends ArrayList<Contact>{
    private static ContactList sInstance = null;

    private ContactList(){}

    public static ContactList getInstance(){
        if (sInstance == null){
            sInstance = new ContactList();
        }
        return sInstance;
    }
}
