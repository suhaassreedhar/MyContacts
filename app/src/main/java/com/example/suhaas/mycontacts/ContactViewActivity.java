package com.example.suhaas.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.graphics.Palette;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactViewActivity extends AppCompatActivity {

    public static final String EXTRA = "CVA_Contact";

    private int mColor;
    private Contact mContact;
    private int mPosition;
    private TextView mContactName;
    private FieldsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        RelativeLayout headerSection = (RelativeLayout) findViewById(R.id.contact_view_header);
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));


        mPosition = getIntent().getIntExtra(EXTRA, 0);
        mContact = ContactList.getInstance().get(mPosition);
        mContactName = (TextView) findViewById(R.id.contact_view_name);


        Toolbar toolbar = (Toolbar)findViewById(R.id.contact_view_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.contact_view_edit) {
                    Intent i = new Intent(ContactViewActivity.this, ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, mPosition);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);

        ListView listView = (ListView) findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact.phoneNumbers, mContact.emails);
        listView.setAdapter(mAdapter);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunset);
        Palette palette = Palette.generate(bitmap);
        mColor = palette.getDarkVibrantSwatch().getRgb();

        updateUI();
    }

    private void updateUI(){
        mContactName.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
    }

    private class FieldsAdapter extends BaseAdapter{
        ArrayList<String> emails;
        ArrayList<String> phoneNumbers;
        FieldsAdapter(ArrayList<String> phoneNumbers, ArrayList<String> emails){
            this.phoneNumbers = phoneNumbers;
            this.emails = emails;

        }
        @Override
        public int getCount() {
            return phoneNumbers.size() +emails.size() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            String value = (String) getItem(position);

            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            contactValue.setText(value);

            ImageView iv = (ImageView) convertView.findViewById(R.id.field_icon);
            if (isFirst(position)){
                if (isEmail(position)){
                    iv.setImageResource(R.drawable.ic_email);
                }else{
                    iv.setImageResource(R.drawable.ic_call);
                }
            }
            iv.setColorFilter(mColor);

            return convertView;
        }

        private boolean isFirst(int position){
            if (position == 0 || position == phoneNumbers.size()){
                return true;
            }
            return false;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (isEmail(position)){
                return emails.get(position - phoneNumbers.size());
            }else{
                return phoneNumbers.get(position);
            }
        }

        private boolean isEmail(int position){
            if (position > phoneNumbers.size()-1){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
