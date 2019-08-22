package com.example.asus.justtrustme;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

//연락처 등록
public class RegisterContacts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    final ArrayList<Contact> datas = new ArrayList<>();
    Button contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_contacts);

        recyclerView = (RecyclerView) findViewById(R.id.listView_contacts);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //adapter = new RecyclerAdapter(datas, this);
        //recyclerView.setAdapter(adapter);

        contacts = (Button) findViewById(R.id.btn_contacts);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            contacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
            });
        }

        /*contacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });*/


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_RC);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private final int REQ_CODE = 100;

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                ) {
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.READ_CONTACTS};

            requestPermissions(permArr, REQ_CODE);
        } else {
            contacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    intent.setData(ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                contacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        intent.setData(ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Contact contact = new Contact();

        switch (requestCode) {
            case (1) :
                if (resultCode == Activity.RESULT_OK) {
                    Cursor c =  getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

                        while(phones.moveToNext()){
                            String tel = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            //showSelectedNumber(name, tel);

                            contact.setName(name);
                            contact.addTel(tel);

                            datas.add(contact);
                        }
                        phones.close();
                        RecyclerAdapter contactAdapter = new RecyclerAdapter(datas, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(contactAdapter);
                    }
                    c.close();
                }
                break;
        }
        /*Uri contactUri = data.getData();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        cursor.moveToFirst();

        while (cursor != null && cursor.moveToNext()){
            String name = cursor.getString(0);
            String number = cursor.getString(1);

            Contact contact = new Contact();

            contact.setName(name);
            contact.addTel(number);

            datas.add(contact);
        }
        if(cursor != null){
            cursor.close();
        }*/
        /*switch(requestCode){
            case(1) :
                if(resultCode == RESULT_OK && data != null){
                    Cursor cursor = getContentResolver().query(data.getData(),
                            new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER},
                            null,null,null);
                    if(cursor.moveToFirst()){
                        contact.setName(cursor.getString(0));
                        contact.addTel(cursor.getString(1));

                        datas.add(contact);
                    }
                }
                break;
        }*/

        /*if(requestCode == 1 && data!=null){
            Contact contact = new Contact();

            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    null,null,null);
            cursor.moveToFirst();

            contact.setName(cursor.getString(0));
            contact.addTel(cursor.getString(1));

            datas.add(contact);

            cursor.close();
        }
        */
    }

    public void showSelectedNumber(String name, String tel) {
        Toast.makeText(this, name + ": " + tel, Toast.LENGTH_LONG).show();
    }

    /*private void getAllContacts() {
        ArrayList<Contact> datas = new ArrayList();
        Contact contact;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contact = new Contact();
                    contact.setName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String tel = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contact.addTel(tel);
                    }

                    phoneCursor.close();

                    datas.add(contact);
                }
            }
            RecyclerAdapter contactAdapter = new RecyclerAdapter(datas, getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(contactAdapter);
        }
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.other_settings:
                Intent intent1 = new Intent(RegisterContacts.this, OtherSettings.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(RegisterContacts.this, Logout.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_book_mark) {
            //Toast.makeText(getApplicationContext(),"else 로 들어왔다.",Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(RegisterContacts.this, BookMark.class);
            startActivity(intent);
        } else if (id == R.id.nav_finding_a_way) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(RegisterContacts.this, FindingWay.class);
            startActivity(intent);
        } else if (id == R.id.nav_notice) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(RegisterContacts.this, Notice.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(RegisterContacts.this, FAQ.class);
            startActivity(intent);
        }
        return true;
    }

}
