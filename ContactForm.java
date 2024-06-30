package com.example.lostandfoundapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class ContactForm extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextOfficePhone;
    private ImageView imageView;
    private Button buttonSave, buttonCancel, buttonPickImage;
    private DatabaseHelper db;
    private int contactId = -1;
    private String currentImageBase64 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        db = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextOfficePhone = findViewById(R.id.editTextOfficePhone);
        imageView = findViewById(R.id.imageView);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonPickImage = findViewById(R.id.buttonPickImage);

        buttonPickImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        // Check if an ID was passed to the activity
        contactId = getIntent().getIntExtra("CONTACT_ID", -1);
        if (contactId != -1) {
            loadContactData(contactId);
        }

        buttonSave.setOnClickListener(v -> saveContact());
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void loadContactData(int id) {
        Contact contact = db.getContact(id);
        if (contact != null) {
            editTextName.setText(contact.getName());
            editTextEmail.setText(contact.getEmail());
            editTextPhone.setText(contact.getPhone());
            editTextOfficePhone.setText(contact.getOfficePhone());
            if (contact.getImageBase64() != null && !contact.getImageBase64().isEmpty()) {
                byte[] bytes = Base64.decode(contact.getImageBase64(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(this, "Unable to load contact data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveContact() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String officePhone = editTextOfficePhone.getText().toString().trim();

        if (contactId == -1) { // If it's a new contact
            db.addContact(new Contact(0, name, email, phone, officePhone, currentImageBase64));
            Toast.makeText(this, "Contact added successfully!", Toast.LENGTH_SHORT).show();
        } else { // If it's an existing contact
            db.updateContact(new Contact(contactId, name, email, phone, officePhone, currentImageBase64));
            Toast.makeText(this, "Contact updated successfully!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    currentImageBase64 = encodeToBase64(bitmap);
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });

    public static String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
