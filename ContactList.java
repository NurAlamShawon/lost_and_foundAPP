package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {

    ListView listViewContacts;
    Button buttonAddContact,buttonBack;
    DatabaseHelper db;
    List<Contact> contacts;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        db = new DatabaseHelper(this);
        listViewContacts = findViewById(R.id.listViewContacts);
        buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonBack=findViewById(R.id.buttonBack);
        contacts = new ArrayList<>();


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listViewContacts.setAdapter(adapter);





        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ContactFormActivity to add a new contact
                Intent intent = new Intent(ContactList.this, ContactForm.class);
                startActivity(intent);
            }
        });
        loadContacts();
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected contact's ID
                Contact selectedContact = contacts.get(position);
                Intent intent = new Intent(ContactList.this, ContactForm.class);
                intent.putExtra("CONTACT_ID", selectedContact.getId());
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        contacts = db.getAllContacts();
        List<String> contactNames = new ArrayList<>();
        for (Contact contact : contacts) {
            contactNames.add(contact.getName());
        }
        adapter.clear();
        adapter.addAll(contactNames);
        adapter.notifyDataSetChanged();
    }
}