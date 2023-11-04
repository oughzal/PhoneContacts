package com.omarcomputer.phonecontacts.viewmodel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omarcomputer.phonecontacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    public MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();
    List<Contact> contactsList = new ArrayList<>();
    Context context;
    public ContactViewModel(@NonNull Application application) {
         super(application);
        this.context = application.getApplicationContext();
    }

    public  String loadContacts() {
        String out="";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setPhoneNumber(phoneNumber);
                    out +=contact.toString() + "\n";

                    contactsList.add(contact);
                    Log.i("ContactTAG",contact.toString());
                }
            } finally {
                cursor.close();
            }
        }
        contacts.setValue(contactsList);
        return out;
    }

    public List<Contact> getContactsList(){
        loadContacts();
        return contactsList;
    }
}
