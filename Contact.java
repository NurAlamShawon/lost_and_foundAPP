package com.example.lostandfoundapp;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String officePhone;
    private String imageBase64;

    public Contact(int id, String name, String email, String phone, String officePhone, String imageBase64) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.officePhone = officePhone;
        this.imageBase64 = imageBase64;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getOfficePhone() { return officePhone; }
    public String getImageBase64() { return imageBase64; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setOfficePhone(String officePhone) { this.officePhone = officePhone; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}

