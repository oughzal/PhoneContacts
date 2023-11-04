package com.omarcomputer.phonecontacts.adaper;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omarcomputer.phonecontacts.R;
import com.omarcomputer.phonecontacts.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private List<Contact> contactList;


    private List<Contact> contactsFiltered;

    public ContactAdapter(List<Contact> itemList){
        this.contactList = itemList;
        this.contactsFiltered = new ArrayList<>(contactList);

    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactsFiltered.get(position);
        Log.i("ContactTAG3",contact.toString());
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhoneNumber());

    }


    @Override
    public int getItemCount() {
        return contactsFiltered.size();
    }
    public void filterContacts(String query) {
        if (query.isEmpty()) {
            contactsFiltered = new ArrayList<>(contactList);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                contactsFiltered = contactList.stream()
                        .filter(contact -> contact.getName().toLowerCase().contains(query.toLowerCase())
                                || contact.getPhoneNumber().contains(query))
                        .collect(Collectors.toList());
            } else {
                // Pour les versions antérieures à Java 8
                contactsFiltered = new ArrayList<>();
                for (Contact contact : contactList) {
                    if (contact.getName().toLowerCase().contains(query.toLowerCase())
                            || contact.getPhoneNumber().contains(query)) {
                        contactsFiltered.add(contact);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView name,phone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            phone = itemView.findViewById(R.id.txtPhone);

        }
    }
}
