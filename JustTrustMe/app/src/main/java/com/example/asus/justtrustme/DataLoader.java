package com.example.asus.justtrustme;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;


public class DataLoader{

    private ArrayList<Contact> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context){
        this.context = context;
    }

    public ArrayList<Contact> get(){
        return datas;
    }

    public void load(){
        ContentResolver resolver = context.getContentResolver();

        String projections[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor cursor = resolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                , projections
                , null
                , null
                , null
        );

        if(cursor.moveToFirst()) {
            Contact contact = new Contact();
            do{
                int idx = cursor.getColumnIndex(projections[0]);
                contact.setName(cursor.getString(idx));
                idx = cursor.getColumnIndex(projections[1]);
                contact.addTel(cursor.getString(idx));

                datas.add(contact);
            }while(cursor.moveToNext());
            /*int idx = cursor.getColumnIndex(projections[0]);
            contact.setId(cursor.getInt(idx));
            idx = cursor.getColumnIndex(projections[1]);
            contact.setName(cursor.getString(idx));
            idx = cursor.getColumnIndex(projections[2]);
            contact.addTel(cursor.getString(idx));

            datas.add(contact);*/
        }

        if(cursor != null){
            cursor.close();
        }
    }
}
