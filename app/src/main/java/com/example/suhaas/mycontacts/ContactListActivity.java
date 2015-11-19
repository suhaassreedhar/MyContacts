package com.example.suhaas.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ContactListActivity extends AppCompatActivity implements ContactListFragment.Contract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        if (getFragmentManager().findFragmentById(R.id.list_fragment_container) == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.list_fragment_container, new ContactListFragment())
                    .commit();
        }
    }

    @Override
    public void selectedPosition(int position) {
        Intent i = new Intent(this, ContactViewActivity.class);
        i.putExtra(ContactViewActivity.EXTRA, position);
        startActivity(i);
    }
}
